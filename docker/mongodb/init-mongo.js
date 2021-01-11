db = db.getSiblingDB('testedb');

db.createUser({
    user: "testedb",
    pwd: "123",
    roles: [{role: "readWrite", db: "testedb"}],
    passwordDigestor: "server"
});

// db.createCollection("usuario");

// db.usuario.drop();

// db.usuario.insert({
// 	"_id" : ObjectId("5d2dd8be6d8fbd164541a4b6"),
// 	"nome" : "admin",
// 	"email" : "admin@gmail.com",
//     "senha" : "$2a$10$7vtQFd.YCWyIH5sGRnNBUeRdFsdHsYMC4AsooaiPxl4KSYjHOTUXu",
// 	"permissao" : "ADMIN"
// });


