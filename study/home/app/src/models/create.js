"use strict";
const UserStorage = require("./userStorage");
class User{
    constructor(body){
        this.body = body;
    }
    create(){
        UserStorage.save(this.body);
    }
}