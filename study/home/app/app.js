"use strict"
const express = require("express");
const home = require("./src/routes/home");
const bodyParser = require("body-parser");
const dotenv = require("dotenv");
dotenv.config();




const app = express();

app.set("views","./src/views");// html
app.set("view engine","ejs");
app.use(express.static(`${__dirname}/src/public`))

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use("/",home);
module.exports = app