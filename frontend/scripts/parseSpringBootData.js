async function updateJsonFile() {
    try {
        //get data
        const response = await fetch('http://localhost:8080/api/configurations/get');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();

        if (data) {
            //change data to fit earlier format, and uses naming to find img name, configuration has all data currently needed, but terrible format
            const transformedData = data.map(item => ({
                carMaker: item.car.make,
                carModel: item.car.model,
                year: item.car.year,
                fuelType: item.fuelType,
                transmissionType: item.tranmissionType,
                numberOfSeats: item.numberOfSeats,
                extraFeatures: item.extraFeatures.filter(feature => feature.valid).map(feature => feature.name),
                price: Object.fromEntries(item.providers.filter(provider => provider.valid).map(provider => [provider.name, provider.price])),
                // naming examples model+name if empty space like "Model 3" then its Model-3 so the img name is Tesla-Model-3
                img: `img/cars/carsLowRes/${item.car.make.replace(/ /g, '-')}-${item.car.model.replace(/ /g, '-')}.jpg`
            }));

            // Read and parse the existing JSON from localStorage
            let json = JSON.parse(localStorage.getItem('cars') || '{}');

            // Update the JSON object
            json.cars = transformedData;

            // Convert the updated JSON object back into a string
            const updatedJson = JSON.stringify(json, null, 2);

            // Write the updated JSON string back to localStorage
            localStorage.setItem('cars', updatedJson);
        } else {
            console.error('Data is null');
        }
    } catch (error) {
        console.error('Error updating JSON file:', error);
    }
}

updateJsonFile().catch(console.error);