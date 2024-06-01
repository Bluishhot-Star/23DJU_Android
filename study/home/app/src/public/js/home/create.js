"user strict"
const name = document.querySelector("#name");
const title = document.querySelector("#create-text1");
const word = document.querySelector("#create-text2");
const save = document.querySelector("#button");

save.addEventListener("click",save);

function create(){
    const req = {
        name: name.value,
        title: title.value,
        word: word.value,
    };
    console.log(req);
//     fetch("/create",{
//         method: "POST",
//         headers: {
//             "Content-Type":"application/json"
//         },
//         body: JSON.stringify(req)
//     }).then((res) => res.json())
//     .then((res) => {
//       if(res.success){
//           location.href = "/database";
//       }else{
//           if (res.err) return alert(res,err);
//           alert(res.msg)

//       }
//     })
//   .catch((err) => {
//       console.error(new Error("글쓰기중 에러"));
//     });
}
