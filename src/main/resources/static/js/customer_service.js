/*
    customers_service.js
 */

class CustomerService {
    constructor() {
    }

    async getMydata() {
        const authToken = localStorage.getItem("authToken");

        if (!authToken) {
            alert("You are not authenticated");
            window.location.href = "/login.html";
            return;    
        }

        try {
            let url = URL_SERVER + 'user/me';

            let config = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                }
            }
    
            return await fetch(url, config);
        } catch (error) {
            console.log(error);
            return;
        }
    }

    async getCustomers(busnessEntityId) {
        const authToken = localStorage.getItem("authToken");

        if (!authToken) {
            alert("You are not authenticated");
            window.location.href = "/login.html";
            return;    
        }

        try {
            let url = URL_SERVER + 'customer/' + busnessEntityId;

            let config = {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                }
            }
    
            return await fetch(url, config);
        } catch (error) {
            console.log(error);
            return;
        }
    }    
    
    async saveCustomer(busnessEntityId, customer) {
        const authToken = localStorage.getItem("authToken");

        if (!authToken) {
            alert("You are not authenticated");
            window.location.href = "/login.html";
            return;    
        }

        try {
            let url = URL_SERVER + 'customer/' + busnessEntityId;
            let methodType = 'POST';

            if (customer.id !== '') {
                url += '/' + customer.id;
                methodType = 'PUT';
            }

            let config = {
                "method": methodType,
                "body": JSON.stringify(customer),
                "headers": {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                }
            };
    
            return await fetch(url, config);
        } catch (error) {
            console.log(error);
            return;
        }
    }

    async deleteCustomer(busnessEntityId, id) {
        const authToken = localStorage.getItem("authToken");

        if (!authToken) {
            alert("You are not authenticated");
            window.location.href = "/login.html";
            return;    
        }

        try {
            let url = URL_SERVER + 'customer/' + busnessEntityId + '/' + id;

            let config = {
                method: 'DELETE',
                "headers": {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                }
            };
    
            return await fetch(url, config);
        } catch (error) {
            console.log(error);
            return;
        }

    }
}

export default CustomerService;