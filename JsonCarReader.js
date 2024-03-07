fetch('Cars.json')
    .then(response => response.json())
    .then(data => {
        const cardsContainer = document.getElementById('cards');
        cardsContainer.innerHTML = '';
        data.cars.forEach(car => createCard(car));
    });


    // main method
function createCard(car) {
    
    const card = document.createElement('div');
    card.className = 'card';

  
    const img = createImageElement(car);
    card.appendChild(img);

    
    const cardInfo = createCardInfoElement(car);
    card.appendChild(cardInfo);

    card.addEventListener('click', function() {
        document.getElementById('expandedCard').innerHTML = card.innerHTML;
    });

    document.getElementById('cards').appendChild(card);
}

   // for the images
function createImageElement(car) {

    const img = document.createElement('img');
    img.src = car.img;
    img.alt = 'car img';
    img.width = 300;
    img.height = 200;
    return img;
}

    // for the card info
function createCardInfoElement(car) {

    const cardInfo = document.createElement('div');
    cardInfo.className = 'card-info';

    const heading = document.createElement('h2');
    heading.textContent = `${car.carMaker} ${car.carModel}`;
    cardInfo.appendChild(heading);

    for (let vendor in car.price) {
        const priceParagraph = document.createElement('p');
        priceParagraph.textContent = `${vendor}: ${car.price[vendor]} NOK/day`;
        cardInfo.appendChild(priceParagraph);
    }

    const locationParagraph = document.createElement('p');
    locationParagraph.textContent = 'Location: Ålesund';
    cardInfo.appendChild(locationParagraph);

    return cardInfo;
}