"use strict";

//navigation menu - нажатие на кнопку "Новые книги"
document.getElementById('space2').onclick=function (){
    // делает текст нажатой ссылки жирным (css класс "active")
    fetch("getListNewBooks")// возвращает ожидание (ответ на посланный запрос), 
                            // которое дальше обрабатывается по цепочке
          .then(status)  
          .then(json)  
          .then(function(data) {  // data содержит ответ сервера преобразованный в js объект 
            printListNewBooks(data); // запускается функция с параметром
            console.log('Request succeeded with JSON response', data);  //вывод в консоль для дебага
          }).catch(function(error) { //срабатывает при ошибке пришедшей с сервера
            console.log('Request failed', error);  
          });
};
document.getElementById('space3').onclick=function (){
     fetch("getListCustomers")
          .then(status)  
          .then(json)
          .then(function(data) {  // data содержит ответ сервера преобразованный в js объект 
            printListCustomers(data); // запускается функция с параметром
            console.log('Request succeeded with JSON response', data);  //вывод в консоль для дебага
          }).catch(function(error) { //срабатывает при ошибке пришедшей с сервера
            console.log('Request failed', error);  
          });
};
function status(response) {  
  if (response.status >= 200 && response.status < 300) {  
    return Promise.resolve(response)  
  } else {  
    return Promise.reject(new Error(response.statusText))  
  }  
}
// Получает ответ в формате Json и формирует из него js объект
function json(response) {  
  return response.json();
};
function printListNewBooks(data){
    let content = document.getElementById('customercontent');
    let cards = '';
    for(let i = 0; i < data.books.length; i++){
        cards +=
        `<div class="card w-25" >
             <img src="${data.books[i].text}" class="card-img-top" alt="...">
            <div class="card-body">
                <h5 class="card-title">${data.books[i].caption}</h5>
                <p class="card-text">${data.books[i].author}. ${data.books[i].publishedYear}</p>
                <a href="buyBook?bookId=${data.books[i].id}" class="btn btn-primary">Купить книгу</a>
            </div>
        </div>`;
    }
    content.innerHTML = cards;
}
function printListCustomers(data){
    let content = document.getElementById('content');
    let cards = '';
        let listAddress = '';
        for(let i=0;i<6;i++){
            for(let j = 0; j < data.customers[i].address.length; j++){
                listAddress+=data.customers[i].address[j].cantry;
                listAddress+=', ';
                listAddress+=data.customers[i].address[j].city;
                listAddress+=', ';
                listAddress+=data.customers[i].address[j].street;
                listAddress+=', ';
                listAddress+=data.customers[i].address[j].house;
                listAddress+=', ';
                listAddress+=data.customers[i].address[j].room;
                listAddress+='';
            }
            cards +=
            `<div class="card w-30 m-3 block" style="width:30%; padding:0%;">
                <div class="card-body">
                    <h5 class="card-title">${data.customers[i].firstname} ${data.customers[i].lastname}</h5>
                    <p class="card-text">Родился: ${data.customers[i].day}.${data.customers[i].month}.${data.customers[i].year}г. Телефон: ${data.customers[i].phone} </p>
                    <p class="card-text">Адресс: ${listAddress}</p>
                    <a href="buyBook?customerId=${data.customers[i].id}" class="btn btn-primary">Изменить данные</a>
                </div>
            </div>`;
        }
    content.innerHTML = cards;
}