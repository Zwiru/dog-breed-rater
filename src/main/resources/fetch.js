fetch('https://dog.ceo/api/breeds/list/all')
    .then(response => response.json())
    .then(data => {
        const breeds = data.message;
        for (const breed in breeds) {
            if (breeds[breed].length > 0) {
                breeds[breed].forEach(sub_breed => {
                    console.log(`insert into dog_breed(breed, rating) values ('${breed}-${sub_breed}', 1000);`);
                });
            } else {
                console.log(`insert into dog_breed(breed, rating) values ('${breed}', 1000);`);
            }
        }
    })
    .catch(error => console.error('Error:', error));