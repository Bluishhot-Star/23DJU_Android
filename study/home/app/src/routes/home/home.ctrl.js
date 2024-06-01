"use strict"

const output = {
    home: (req,res) => {
        res.render("home/index.ejs",{
            javascriptKey: process.env.javascriptkey
        })
    },
    database: (req,res) => {
        res.render('home/database.ejs');
    },
    API: (req,res) => {
        res.render("home/API.ejs")
    },
    mysql: (req,res) => {
        res.render("home/mysql.ejs")
    },
    create:(req,res) => {
        res.render("home/create.ejs")
    },
    
};

/*const process = {
    create: (req,res) => {
        const user =  new User(req.body);
        const response = user.create()
        return res.json(response);
    }
}*/



module.exports = {
    output,
    process,
}