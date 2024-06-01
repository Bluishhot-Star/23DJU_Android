"use strict"
const express = require("express");
const router = express.Router();
const ctrl = require("./home.ctrl");

router.get("/",ctrl.output.home);
router.get("/database",ctrl.output.database);
router.get("/API",ctrl.output.API);
router.get("/mysql",ctrl.output.mysql);
router.get("/create",ctrl.output.create);

router.post("/create",ctrl.process.create);

module.exports = router;