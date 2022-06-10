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

app.get('/Extra2', (req, res) => {
  res.render('Extra2.ejs');
  res.status(200);
});

app.get('/Extra3', (req, res) => {
  res.render('Extra3.ejs');
  res.status(200);
});

app.use((req, res) => {
  res.status(404);
  res.send('<h1>404 not found</h1>')
});

const PORT = parseInt(process.env.PORT) || 8080;
app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});