async function updateJsonFile() {
    try {
        //clean old data
        localStorage.removeItem('cars');

        //get data
        const response = await fetch('http://localhost:8080/api/cars/get');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();

        if (data) {
            // Add img property to each item and each configuration
            const updatedData = data.map(item => {
                item.configurations = item.configurations.map(config => {
                    config.img = `img/cars/carsLowRes/${item.make.replace(/ /g, '-')}-${item.model.replace(/ /g, '-')}.jpg`;
                    return config;
                });
                return item;
            });

            

            
            let json = JSON.parse(localStorage.getItem('cars') || '{}');

            // Update the JSON object
            json.cars = updatedData;

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
    // run car-reader-local-storage.js
if (window.initCarReader) {
    window.initCarReader();
}
}

updateJsonFile().catch(console.error);

document.dispatchEvent(new Event('parseSpringBootDataCompleted'));

