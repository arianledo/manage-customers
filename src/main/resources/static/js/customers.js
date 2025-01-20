/*
    customers.js
 */

import CustomerService from './customer_service.js';

class Customers {
    static customers = [];
    static formCustomerId = '';

    constructor() {
        this.renderCustomers();

        document.getElementById('btn-add-customer').addEventListener('click', this.onClickAdd);

        document.getElementById('btn-save-customer').addEventListener('click', this.saveUser);
    }

    async renderCustomers() {
        const customerService = new CustomerService();

        customerService.getCustomers()
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error in request: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                //show data in the table
                this.showData(data);
            })
            .catch(error => {
                console.error('There was a problem with the request:', error);
            });
    }

    showData(data) {
        let row;
        const tbody = document.getElementById('tbody-customers');
        tbody.innerHTML = ''; // clean

        data.forEach(customer => {
            row = this.getRow(customer);

            tbody.appendChild(row);

            Customers.customers.push(customer);

            document.getElementById('btn-edit-'+customer.id).addEventListener('click', this.onClickUpdate);
            document.getElementById('btn-delete-'+customer.id).addEventListener('click', this.onClickRemove);
        });
    }

    getRow(customer) {
        let dateObject;
        if (customer.creationDate !== undefined) {
            let date = customer.creationDate;
            dateObject = new Date(date);
        } else {
            dateObject = new Date();
        }

        const row = document.createElement('tr');

        row.setAttribute('id', 'tr-'+customer.id);

        row.innerHTML = `
            <td id="td-name-${customer.id}">${customer.firstname} ${customer.lastname}</td>
            <td id="td-email-${customer.id}">${customer.email}</td>
            <td id="td-phone-${customer.id}">${customer.phone}</td>
            <td id="td-address-${customer.id}">${customer.address}</td>
            <td>${dateObject.getMonth()+1}-${dateObject.getDate()}-${dateObject.getFullYear()}</td>
            <td>  
                <a href="#" data-id="${customer.id}" id="btn-edit-${customer.id}" class="btn btn-primary btn-circle btn-sm" data-toggle="modal" data-target="#userModal">
                    <i class="fas fa-edit"></i>
                </a>
                <a href="#" data-id="${customer.id}" id="btn-delete-${customer.id}" class="btn btn-danger btn-circle btn-sm">
                    <i class="fas fa-trash"></i>
                </a>
            </td>
        `;

        return row;
        // Agregar la nueva fila a la tabla
        //$('#miTabla').bootstrapTable('append', nuevaFila);
    }

    onClickUpdate(event) {
        let elementEvent = event.currentTarget;
        Customers.FormCustomerId = elementEvent.getAttribute('data-id');

        var titleElementForm = document.getElementById("userModalLabel");
        titleElementForm.innerHTML = "Update Customer";

        let customer;
        for (const c of Customers.customers) {
            if (Customers.FormCustomerId == c.id) {
                customer = c;
                break;
            }
        }

        //Clear inputs of form
        document.getElementById('txtFirstname').value = customer.firstname;
        document.getElementById('txtLastname').value = customer.lastname;
        document.getElementById('txtEmail').value = customer.email;
        document.getElementById('txtPhone').value = customer.phone;
        document.getElementById('txtAddress').value = customer.address;
    }

    onClickAdd() {
        Customers.FormCustomerId = '';

        var titleElementForm = document.getElementById("userModalLabel");
        titleElementForm.innerHTML = "Create Customer";

        //Clear inputs of form
        document.getElementById('txtFirstname').value = '';
        document.getElementById('txtLastname').value = '';
        document.getElementById('txtEmail').value = '';
        document.getElementById('txtPhone').value = '';
        document.getElementById('txtAddress').value = '';
    }

     async onClickRemove(event) {
         const customerService = new CustomerService();
         let response = confirm("Do you want remove this?");
         if (!response) {
             return;
         }

         let elementEvent = event.currentTarget;
         let customerId = elementEvent.getAttribute('data-id');

         console.log(customerId);
         await customerService.deleteCustomer(customerId)
             .then(() => {
                 const element = document.getElementById('tr-' + customerId);
                 if (element) {
                         element.remove();
                 }
             })
             .catch(error => {
                 console.error(error);
             });
     }

    async saveUser() {
        const customerService = new CustomerService();

        let id = Customers.FormCustomerId;
        let firstname = document.getElementById('txtFirstname').value;
        let lastname = document.getElementById('txtLastname').value;
        let email = document.getElementById('txtEmail').value;
        let phone = document.getElementById('txtPhone').value;
        let address = document.getElementById('txtAddress').value;

        let customer = {
            "id": id,
            "firstname": firstname,
            "lastname": lastname,
            "email": email,
            "phone": phone,
            "address": address
        };
        console.log(customer);

        await customerService.saveCustomer(customer);
        Customers.customers.push(customer);

        if(Customers.FormCustomerId === '') {
            console.log('despues de crear');
            const tbody = document.getElementById('tbody-customers');

             //let newRow = this.getRow(customer);

            let dateObject = new Date();

            const row = document.createElement('tr');

            row.setAttribute('id', "tr-" + customer.id);

            row.innerHTML = `
                <td id="td-name-${customer.id}">${customer.firstname} ${customer.lastname}</td>
                <td id="td-email-${customer.id}">${customer.email}</td>
                <td id="td-phone-${customer.id}">${customer.phone}</td>
                <td id="td-address-${customer.id}">${customer.address}</td>
                <td>${dateObject.getMonth()+1}-${dateObject.getDate()}-${dateObject.getFullYear()}</td>
                <td>
                    <a href="#" data-id="${customer.id}" id="btn-edit-${customer.id}" class="btn btn-primary btn-circle btn-sm" data-toggle="modal" data-target="#userModal">
                        <i class="fas fa-edit"></i>
                    </a>
                    <a href="#" data-id="${customer.id}" id="btn-delete-${customer.id}" class="btn btn-danger btn-circle btn-sm">
                        <i class="fas fa-trash"></i>
                    </a>
                </td>
            `;

            tbody.appendChild(row);
        } else {
            console.log('despues de actualizar');
            console.log(customer);
            document.getElementById('td-name-'+customer.id).innerHTML = customer.firstname+' '+customer.lastname;
            document.getElementById('td-email-'+customer.id).innerHTML = customer.email;
            document.getElementById('td-phone-'+customer.id).innerHTML = customer.phone;
            document.getElementById('td-address-'+customer.id).innerHTML = customer.address;
         }
    }
}

const customers = new Customers();