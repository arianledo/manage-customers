/*
    customers.js
 */

import UtilService from './util_service.js';

import CustomerService from './customer_service.js';

class Customers {
    static customers = [];
    static editIndex = 0;
    static formCustomerId = '';
    
    static BUSINESS_NAME = '';
    static USERNAME = '';
    static EMAIL_USER = '';
    static BUSINESS_ENTITY_Id = 0;
    
    static customerService = new CustomerService();

    static tbody = document.getElementById('tbody-customers');

    constructor() {
        this.getMydata();

        document.getElementById('btn-add-customer').addEventListener('click', this.onClickAdd);

        document.getElementById('btn-save-customer').addEventListener('click', this.saveUser);
    }

    async getMydata() {
        const utilService = new UtilService();

        utilService.getMydata()
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error in request: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                //Set data
                Customers.BUSINESS_NAME = data.businessEntityName;
                Customers.USERNAME = data.username;
                Customers.EMAIL_USER = data.emailUser;
                Customers.BUSINESS_ENTITY_Id = data.businessEntityId;

                document.getElementById("username").innerHTML = Customers.USERNAME;
                let spanBusinessEntity = document.getElementById("businessEntity");
                spanBusinessEntity.innerHTML = Customers.BUSINESS_NAME;
               // spanBusinessEntity.dataset.businessid = BUSINESS_ENTITY_Id;

                this.renderCustomers();
            })
            .catch(error => {
                console.error('There was a problem with the request:', error);
            });
    }

    async renderCustomers() {
        Customers.customerService.getCustomers(Customers.BUSINESS_ENTITY_Id)
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
        Customers.tbody.innerHTML = ''; // clean

        data.forEach(customer => {
            Customers.appendChildTBody(customer);

            document.getElementById('btn-edit-'+customer.id).addEventListener('click', this.onClickUpdate);
            document.getElementById('btn-delete-'+customer.id).addEventListener('click', this.onClickRemove);
        });


    }

    static appendChildTBody(customer) {
        
        Customers.tbody.appendChild(Customers.getRow(customer));

        Customers.customers.push(customer);
    }

    static getRow(customer) {
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
    }

    onClickUpdate(event) {
        let elementEvent = event.currentTarget;
        Customers.FormCustomerId = elementEvent.getAttribute('data-id');

        var titleElementForm = document.getElementById("userModalLabel");
        titleElementForm.innerHTML = "Update Customer";

        let customer;
        for (let i=0; i<Customers.customers.length; i++) {
            if (Customers.FormCustomerId == Customers.customers[i].id) {
                customer = Customers.customers[i];
                Customers.editIndex = i;
                break;
            }
        }

        //Initializer inputs of form
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
         let responseModal = confirm("Do you want remove this?");
         if (!responseModal) {
             return;
         }

         let elementEvent = event.currentTarget;
         let customerId = elementEvent.getAttribute('data-id');

            try {
                const response = await Customers.customerService.deleteCustomer(Customers.BUSINESS_ENTITY_Id, customerId);

                if (response.status === 204) {
                    const element = document.getElementById('tr-' + customerId);
                    if (element) {
                        element.remove();
                    }
                    alert("The customer was eliminated correctly");
                } else {
                    throw new Error('Error in request: ' + response.statusText);
                }
            } catch(error) {
                 console.error(error);
            }   
     }

     async saveUser() {
        let customer = {
            "id": Customers.FormCustomerId,
            "firstname": document.getElementById('txtFirstname').value,
            "lastname": document.getElementById('txtLastname').value,
            "email": document.getElementById('txtEmail').value,
            "phone": document.getElementById('txtPhone').value,
            "address": document.getElementById('txtAddress').value
        };

        Customers.customerService.saveCustomer(Customers.BUSINESS_ENTITY_Id, customer)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error in request: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                //show data in the table
                if(Customers.FormCustomerId === '') {
                    Customers.appendChildTBody(data);

                    document.getElementById('btn-edit-'+data.id).addEventListener('click', this.onClickUpdate);
                    document.getElementById('btn-delete-'+data.id).addEventListener('click', this.onClickRemove);
                    
                    alert("The customer was added correctly");
                } else {
                    document.getElementById('td-name-'+customer.id).innerHTML = customer.firstname+' '+customer.lastname;
                    document.getElementById('td-email-'+customer.id).innerHTML = customer.email;
                    document.getElementById('td-phone-'+customer.id).innerHTML = customer.phone;
                    document.getElementById('td-address-'+customer.id).innerHTML = customer.address;

                    Customers.customers[Customers.editIndex] = customer;
                    
                    alert("The customer was updated correctly");
                }
            })
            .catch(error => {
                console.error('There was a problem with the request:', error);
            });        
    }
    
}

const customers = new Customers();