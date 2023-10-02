function getAuthHeaders() {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        alert('Not authenticated!');
        window.location.href = 'login.html'; // Redirect to login or other appropriate page
        return {};
    }
    return {
        'Authorization': 'Bearer ' + token
    };
}

function handleResponse(response) {
    if (!response.ok) {
        throw new Error('Network response was not ok: ' + response.statusText);
    }
    return response.json();
}

// Function to create a user card
function createProductCard(user) {
    const card = $('<div class="col-lg-3 col-md-4 col-sm-6 mt-3"></div>');
    const cardInner = $('<div class="card mx-auto mb-2" style="background-color:transparent;"></div>');
    const cardBody = $('<div class="card-body"></div>');
    
    const id = $(`<p class="card-text">ID: ${product.id}</p>`);
    const productName = $(`<p class="card-text">Geschlecht: ${product.name}</p>`);
    const price = $(`<p class="card-text">First Name: ${product.price} </p>`);
    const description = $(`<p class="card-text">Last Name: ${product.description}</p>`);
    const quantity = $(`<p class="card-text">Adresse: ${product.quantity}</p>`);
    const category = $(`<p class="card-text">Door Number: ${product.category}</p>`);
    const imageURL = $(`<p class="card-text">Postal Code: ${product.imageURL}</p>`);

const editButton = $('<button class="btn btn-primary me-3">Edit</button>');
const deleteButton = $('<button class="btn btn-primary">Delete</button>');

editButton.on('click', (e) => {
    e.stopPropagation();  // Prevents the card from toggling when the button is clicked
    console.log('Edit button clicked for product:', product.id);
});

deleteButton.on('click', (e) => {
    e.stopPropagation();  // Prevents the card from toggling when the button is clicked
    console.log('Delete button clicked for product:', product.id);
});

cardBody.append(id, productName, price, description, quantity, category, imageURL);
cardInner.append(cardBody);
card.append(cardInner);

return card;
}

// First, fetch the current user details
fetch("http://localhost:8080/users/current-user", {
    method: "GET",
    headers: getAuthHeaders()
})
    .then(handleResponse)
    .then(currentUser => {
        if (currentUser.role === "ROLE_ADMIN") {
            return fetch("http://localhost:8080/products", {
                headers: getAuthHeaders()
            });
        } else {
            throw new Error('User is not an admin');
        }
    })
    .then(handleResponse)
    .then(users => {
        const userCardContainer = $('#productsCardContainer'); // Add a container in your HTML where user cards will be displayed
        users.forEach(user => {
            const userCard = createProductCard(user);
            userCardContainer.append(userCard);


            // Once data is populated, display the content
            document.getElementById('productsCardContainer').style.display = 'flex';
            document.getElementById('admin-header').style.display = 'block';
            document.getElementById('new-product-button').style.display = 'block';
            
        });
    })
    .catch(error => {
        console.error('Error:', error.message);
        document.getElementById('error-message').style.display = 'block';
    });