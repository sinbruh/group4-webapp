function fetchAndCreateCards() {
    try {
        const data = JSON.parse(localStorage.getItem('cars'));
        console.log(data);

        if (data && data.cars) {
            createCards(data);
        } else {
            console.error('Data is null or does not contain cars');
            fetch('../backend/cars.json')
                .then(response => response.json())
                .then(data => {
                    createCards(data);
                })
                .catch(error => {
                    console.error('Error fetching cars data:', error);
                });
        }
    } catch (error) {
        console.error('Error fetching cars data:', error);
    }
}

function createCards(data) {
    // Removes prior cards and expanded cards
    const cardsContainer = document.getElementById('cards');
    cardsContainer.innerHTML = '';
    const expandedCard = document.getElementById('expandedCard');
    expandedCard.innerHTML = '';
   
    
    

    // Creates a card for each car with the createCard method
    data.cars.forEach(car => {
        // Gets lowest price for a car and checks it to price range
        const lowestPrice = Math.min(...Object.values(car.price));
        
        const fromPrice = parseInt(document.getElementById('fromPrice').value);
        const toPrice = parseInt(document.getElementById('toPrice').value);
       
        if (lowestPrice >= fromPrice && lowestPrice <= toPrice) {
            createCard(car);
        } 
    });
}

window.onload = function() {
        // refreshes the cards when the page is loaded
    fetchAndCreateCards();
    
       // refreshes cards when price range is changed
    document.getElementById('fromPrice').addEventListener('change', fetchAndCreateCards);
    document.getElementById('toPrice').addEventListener('change', fetchAndCreateCards);
}
        // main method
    function createCard(car) {
        
        const card = document.createElement('div');
        card.className = 'card';
    
      
        const img = createImageElement(car);
        card.appendChild(img);
    
        
        const cardInfo = createCardInfoElement(car);
        card.appendChild(cardInfo);
    
    
        //method for expanded card
        card.addEventListener('click', function() {
            const expandedCard = document.getElementById('expandedCard');
            expandedCard.innerHTML = card.innerHTML;
    
            //obtains info from original card, and adds more info from Json
            const additionalInfo = document.createElement('div');
            additionalInfo.innerHTML = `<p>Year: ${car.year}</p>
                            <p>Fuel Type: ${car.fuelType}</p>
                            <p>Transmission Type: ${car.transmissionType}</p>
                            <p>Number of Seats: ${car.numberOfSeats}</p>
                            <p>Extra Features: ${car.extraFeatures.join(', ')}</p>`;
            expandedCard.appendChild(additionalInfo);
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
    