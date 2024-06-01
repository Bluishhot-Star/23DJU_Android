// "use strict"
// const mysql = require("mysql");
// const db = mysql.createConnection({
//     host: process.env.DB_HOST,
//     user: process.env.DB_USER,
//     port:'3306',
//     password: process.env.DB_PSWORD,
//     database: process.env.DB_DATABASE,
// });

// module.exports = {
//     init: function () {
//         return mysql.createConnection(db);
//     },
//     connect: function(conn) {
//         conn.connect(function(err) {
//             if(err) console.error('mysql connection error : ' + err);
//             else console.log('mysql is connected successfully!');
//         });
//     }
// }