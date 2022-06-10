# app.py
import numpy as np
import tensorflow as tf
from flask import Flask, json, request, jsonify, Response
import os
from werkzeug.utils import secure_filename
from PIL import Image
from tqdm import tqdm
import tensorflow_datasets as tfds

app = Flask(__name__)

UPLOAD_FOLDER = 'static/uploads/training/money50fake'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 5 * 1024 * 1024

ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg'])


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/')
def main():
    return 'Homepage'


@app.route('/upload', methods=['POST'])
def upload_file():
    # check if the post request has the file part
    if 'img' not in request.files:
        resp = jsonify({'message': 'File yg dikirim tidak ditemukan'})
        resp.status_code = 400
        return resp

    files = request.files.getlist('img')

    errors = {}
    success = False

    for file in files:
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            success = True
        else:
            errors[file.filename] = 'Jenis file tidak bisa di upload'

    if success and errors:
        errors['message'] = 'File gagal di upload, internal server error'
        resp = jsonify(errors)
        resp.status_code = 500
        return resp
    if success:
        resp = jsonify({'message': 'FIle berhasil di upload'})
        resp.status_code = 201

        # ------------------------------------------------------------------------------------- 1

        builder = tfds.ImageFolder('./static/uploads', shape=(224, 224, 3))
        print(builder.info)  # num examples, labels... are automatically calculated
        ds = builder.as_dataset(split='training', shuffle_files=False, as_supervised=True)

        # ---------------------------------------------------------------------------------------1.2

        def format_image(image, label):
            image = tf.image.resize(image, (224, 224)) / 255.0
            return image, label

        test_batches = ds.map(format_image).batch(1)
        # ----------------------------------------------------------------------------------------1.3

        # Load TFLite model and allocate tensors.
        tflite_model_file = 'model3.tflite'
        interpreter = tf.lite.Interpreter(model_path=tflite_model_file)
        interpreter.allocate_tensors()

        input_index = interpreter.get_input_details()[0]["index"]
        output_index = interpreter.get_output_details()[0]["index"]

        predictions = []

        test_labels, test_imgs = [], []
        for img, label in tqdm(test_batches.take(1)):
            interpreter.set_tensor(input_index, img)
            interpreter.invoke()
            predictions.append(interpreter.get_tensor(output_index))

            test_labels.append(label.numpy()[0])
            test_imgs.append(img)

        score = 0
        for item in range(0, 1):
            prediction = np.argmax(predictions[item])
            label = test_labels[item]
            if prediction == label:
                score = score + 1

        print("\nOut of 1 predictions I got " + str(score) + " correct")

        # ------------------------------------------------------------------------------2

        class_names = ['money100fake', 'money100real', 'money50fake', 'money50real']

        # ------------------------------------------------------------------------------- 3

        predictions = np.array(predictions)
        predictions = predictions.flatten()

        a1 = str(predictions[0])
        a2 = str(predictions[1])
        a3 = str(predictions[2])
        a4 = str(predictions[3])
        # print(d['1'])
        returnData = {
            'r_0': a1, 'r_1': a2, 'r_2': a3, 'r_3': a4
        }
        # data = json.load(f)

        # remove image
        # os.remove(os.path.join(app.config['UPLOAD_FOLDER'], filename))

        resp.status_code = 200
        # return Response(response= data , status=200)
        return jsonify(returnData)
    else:
        resp = jsonify(errors)
        resp.status_code = 500
        return resp


if __name__ == '__main__':
    app.run(port=8080, debug=True)