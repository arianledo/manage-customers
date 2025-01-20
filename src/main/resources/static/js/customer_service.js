class CustomerService {
    constructor() {
    }

    async deleteCustomer(id) {
        let url = URL_SERVER + 'customer/' + id;
        let config = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': sessionStorage.token
            }
        };

        await fetch(url, config);
        alert("The customer was eliminated correctly");
    }

    async getCustomers() {
        let url = URL_SERVER + 'customer';

        let config = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': sessionStorage.token
            }
        }

        return await fetch(url, config);
    }

    async saveCustomer(customer) {

        let url = URL_SERVER + 'customer';
        let methodType = 'POST';
        let messageAlert = 'The customer was added correctly'

        if (customer.id !== '') {
            url += '/' + customer.id;
            methodType = 'PUT';
            messageAlert = 'The customer was updated correctly'
        }

        let config = {
            "method": methodType,
            "body": JSON.stringify(customer),
            "headers": {
                'Content-Type': 'application/json'
            }
        };

        await fetch(url, config);
        alert(messageAlert);
    }
}

export default CustomerService;