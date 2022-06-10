const express = require('express');
const path = require('path');

const app = express();
app.set('view engine', 'ejs');

//Setting directory untuk render dan static (untuk render default views)
app.set('views', path.join(__dirname, '/public'));
app.use(express.static('public'))

// Display a form for uploading files.
app.get('/', (req, res) => {
    res.render('Extra1_2.ejs');
    res.status(200);
  });

// button.addEventListener('click', function(e) {
//     console.log('button was clicked');
//     window.location="./download"
//     // or window.open(url, '_blank') for new window.
//   });

//route to download a file
app.get('/download',(req, res) => {
  var file = 'SmartMoneyRecognition.apk';
  var fileLocation = path.join('./public',file);
  console.log(fileLocation);
  res.download(fileLocation, file);
});

const PORT = parseInt(process.env.PORT) || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});