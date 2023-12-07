
console.log('In message.js');

class viewHelper {

	// Retrieve an element from the DOM
	static getElement(selector) {
		const element = document.querySelector(selector)

		return element;
	}

	// Create an element with an optional CSS class
	static createElement(tag, classNames) {
		const element = document.createElement(tag)
		
		for (var className of classNames) {
			element.classList.add(className)
		}
		return element;
	}

}

class ModelHelper {
	static filterAndValidate(message) {
		message = encodeURI(message);
		return message;
	}
}

class MessageModel {
	constructor() {
		this.initialize();
	}
	
	initialize() {
		this.getMessageData();
		
	}
	
	getMessageData() {

		console.log(document.cookie);

		console.log('In GetMessage()');
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(this.responseText);
				
				this.messages = JSON.parse(this.responseText);
					
				//this.messages.data.forEach((item)=>{
				//	item.message = ModelHelper.filterAndValidate(item.message);
				//})


				const element = document.querySelector('#root');
				let event = new CustomEvent('GetMessageData', {detail:this.messages});
				element.dispatchEvent(event);
			 
			}
		};
		xhttp.open("GET", "http://localhost:8888/api/message", true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	}

	deleteMessage(id){
		console.log('In DeleteMessage()');
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(this.responseText);
				
				const element = document.querySelector('#root');

				let data = {response:this.responseText, messageid: id};
				let event = new CustomEvent('MessageDeleted', {detail:data});
				element.dispatchEvent(event);
			 
			}

		};

		let url = `http://localhost:8888/api/message/${id}`;

		xhttp.open("DELETE", url, true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	}

	addMessage(messageid, authorid, message){

		//message = ModelHelper.filterAndValidate(message);

		console.log('In addMessage()');
		console.log(messageid);

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				console.log(this.responseText);
				
				const element = document.querySelector('#root');

				let data = {response:this.responseText};
				let event = new CustomEvent('MessageAdded', {detail:data});
				element.dispatchEvent(event);
			}

			if (this.readyState == 4 && this.status == 400) {
				console.log(this.responseText);

				let response = JSON.parse(this.responseText);

				const element = document.querySelector('#root');

				let data = {errors: response.errormessage};
				let event = new CustomEvent('ShowAlert', {detail:data});
				element.dispatchEvent(event);
			}


		};

		let url;
		if (messageid)
			url = `http://localhost:8888/api/message/${messageid}`;
		else
			url = `http://localhost:8888/api/message`;

		xhttp.open("POST", url, true);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send(JSON.stringify({authorid: authorid, message: message}));


	}


}

class MessageView {
	constructor() {
	}
	
	createView(messageData) {
		

		this.messageData = messageData.data;
		
		this.app = viewHelper.getElement('#root');

		this.app.replaceChildren()
		let title = this.createTitle();
		let cards = this.createCards();
		
		let container = viewHelper.createElement('div', ['container']);
		container.append(title, cards);
		
		this.app.append(container);
	}

	createTitle() {
		let title = viewHelper.createElement('div', ['title','h3', 'mt-4','mb-4']);
		title.textContent = 'Messages';
		return title;
	}

	
	createCards() {
		console.log('Ready to Create Cards');
		
		let cardDeck = viewHelper.createElement('div', ['card-deck']);
		
		//Create Message Cards
		for(var message of this.messageData) {
		
			let card = viewHelper.createElement('div', ['card']);
			card.addEventListener('click', app.handleCardClick);
			card.data_messageid = message.messageid;
			
			
			let cardBody = viewHelper.createElement('div', ['card-body']);
			let cardTitle = viewHelper.createElement('div', ['card-title']);
			cardTitle.textContent = message.authorname;
			let cardText = viewHelper.createElement('p', ['card-text']);

			cardText.textContent = message.message;
		
			cardBody.append(cardTitle, cardText);
			card.append(cardBody);
			cardDeck.append(card);
		}

		//Create "Add Message" card
		let card = viewHelper.createElement('div', ['card']);
		card.addEventListener('click', ()=>app.handleAddCardClick());
		

		
		let cardBody = viewHelper.createElement('div', ['card-body']);
		let cardTitle = viewHelper.createElement('div', ['card-title']);
		cardTitle.textContent = '+';
		let cardText = viewHelper.createElement('p', ['card-text']);
	
		cardBody.append(cardTitle, cardText);
		card.append(cardBody);
		cardDeck.append(card);

		return cardDeck;
	}

	createMessageModal(messageid){

		let message = this.messageData.find(x=>x.messageid === messageid);
		let modalTitle = viewHelper.getElement('#messageModalLabel');
		modalTitle.textContent = message.authorname;

		let messageRow = this.createDataRow('Message', message.message);
		let alterRow = this.createAlterRow(messageid);

		let modalBody = viewHelper.getElement('#messageModalBody');
		modalBody.replaceChildren();
		modalBody.append(messageRow, alterRow);


		let btnFooterClose = viewHelper.createElement('button', ['btn','btn-primary']);
		btnFooterClose.setAttribute('type', 'button');
		btnFooterClose.setAttribute('data-dismiss', 'modal');
		btnFooterClose.textContent = 'Close';
		let modalFooter = viewHelper.getElement('#messageModalFooter');
		modalFooter.replaceChildren();
		modalFooter.append(btnFooterClose);

		const modal = document.querySelector('#messageModal');
		$('#messageModal').modal('toggle');

	}

	createAddMessageModal(){

		let modalTitle = viewHelper.getElement('#messageModalLabel');
		modalTitle.textContent = 'Add Message';

		let authorRow = this.createInputRow('AuthorId', 'authorid', '');
		let messageRow = this.createInputRow('Message', 'message', '');
	
		let modalBody = viewHelper.getElement('#messageModalBody');
		modalBody.replaceChildren();
		modalBody.append( authorRow, messageRow);


		let btnFooterSave = viewHelper.createElement('button', ['btn','btn-primary']);
		btnFooterSave.setAttribute('type', 'button');
		btnFooterSave.addEventListener('click', ()=>app.handleSaveMessageClick());
		btnFooterSave.textContent = 'Save';
		let btnFooterCancel = viewHelper.createElement('button', ['btn','btn-outline-secondary']);
		btnFooterCancel.setAttribute('type', 'button');
		btnFooterCancel.setAttribute('data-dismiss', 'modal');
		btnFooterCancel.textContent = 'Cancel';
		let modalFooter = viewHelper.getElement('#messageModalFooter');
		modalFooter.replaceChildren();
		modalFooter.append( btnFooterCancel, btnFooterSave);
		

		const modal = document.querySelector('#messageModal');
		$('#messageModal').modal('toggle');

	}

	createEditMessageModal(messageid){

		let message = this.messageData.find(x=>x.messageid === messageid);

		console.log(message);

		let modalTitle = viewHelper.getElement('#messageModalLabel');
		modalTitle.textContent = 'Edit Message';

		let authorRow = this.createInputRow('Author Id', 'authorid', message.authorid);
		let messageRow = this.createInputRow('Message', 'message', message.message);

		let modalBody = viewHelper.getElement('#messageModalBody');
		modalBody.replaceChildren();
		modalBody.append( authorRow, messageRow);


		let btnFooterSave = viewHelper.createElement('button', ['btn','btn-primary']);
		btnFooterSave.setAttribute('type', 'button');
		btnFooterSave.addEventListener('click', ()=>app.handleSaveMessageClick(messageid));
		btnFooterSave.textContent = 'Save';
		let btnFooterCancel = viewHelper.createElement('button', ['btn','btn-outline-secondary']);
		btnFooterCancel.setAttribute('type', 'button');
		btnFooterCancel.setAttribute('data-dismiss', 'modal');
		btnFooterCancel.textContent = 'Cancel';
		let modalFooter = viewHelper.getElement('#messageModalFooter');
		modalFooter.replaceChildren();
		modalFooter.append( btnFooterCancel, btnFooterSave);
		
	}


	createDataRow(label, data) {
		let row = viewHelper.createElement('div', ['form-group', 'row']);
		let labelColumn = viewHelper.createElement('label', ['col-sm-2','col-form-label']);
		labelColumn.textContent = label;
		let fieldColumn = viewHelper.createElement('div', ['col-sm-10']);
		let fieldText = viewHelper.createElement('label', ['form-control-plaintext']);
		fieldText.textContent = data;
		fieldColumn.append(fieldText);
		row.append(labelColumn, fieldColumn);
		return row;
	}

	createInputRow(label, name, data) {
		let row = viewHelper.createElement('div', ['form-group', 'row']);
		let labelColumn = viewHelper.createElement('label', ['col-sm-2','col-form-label']);
		labelColumn.textContent = label;
		let fieldColumn = viewHelper.createElement('div', ['col-sm-10']);
		let fieldText = viewHelper.createElement('input', ['form-control']);
		fieldText.setAttribute('id', name);
		fieldText.value = data;
		fieldColumn.append(fieldText);
		row.append(labelColumn, fieldColumn);
		return row;
	}

	createAlterRow(id) {
		let row = viewHelper.createElement('div', ['form-group', 'row']);
		let labelColumn = viewHelper.createElement('label', ['col-sm-2','col-form-label']);
		labelColumn.textContent = '';
		let fieldColumn = viewHelper.createElement('div', ['col-sm-10']);

//		let btnDelete = viewHelper.createElement('button', ['btn','btn-secondary']);
//		btnDelete.textContent = 'Delete';
//		btnDelete.addEventListener('click', ()=>app.handleDeleteCard(id));

		let btnEdit = viewHelper.createElement('button', ['btn','btn-secondary']);
		btnEdit.textContent = 'Edit';
		btnEdit.addEventListener('click', ()=>app.handleOpenEditCard(id));
		btnEdit.data_messageid = id;

		fieldColumn.append(btnEdit);
//		fieldColumn.append(btnDelete, btnEdit);
		row.append(labelColumn, fieldColumn);
		return row;
	}

	

	createAlert(data) {


		data.errors.forEach((item) => {
			let alerts = document.querySelector('#alerts');
			let alert = viewHelper.createElement('div', ['alert','alert-warning','alert-dismissible','fade','show']);
			alert.setAttribute('role', 'alert');
			alert.innerHTML = item;
	
			let btnClose = viewHelper.createElement('button', ['close']);
			btnClose.setAttribute('type', 'button');
			btnClose.setAttribute('data-dismiss', 'alert');
			btnClose.setAttribute('aria-label', 'Close');
			btnClose.innerHTML = '<span aria-hidden="true">&times;</span>';
	
			alert.append(btnClose);
			alerts.append(alert);
	
		});


//		console.log(data);
	//	<div class="alert alert-warning alert-dismissible fade show" role="alert">
	//	<strong>Holy guacamole!</strong> You should check in on some of those fields below.
	//	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
	//		<span aria-hidden="true">&times;</span>
	//	</button>
	//	</div>
	}
}

class MessageController {
	constructor(model, view) {
		this.model = model;
		this.view = view;

		const element = document.querySelector('#root');
		element.addEventListener('GetMessageData', function(event) {
			app.handleMessageData(event.detail);
		});
		element.addEventListener('MessageDeleted', function(event) {
			app.handleMessageDeleted(event.detail);
		});
		element.addEventListener('MessageAdded', function(event) {
			app.handleMessageAdded(event.detail);
		});
		element.addEventListener('ShowAlert', function(event) {
			app.handleShowAlert(event.detail);
		});
	}
	
	handleMessageData(message){
		console.log('create view');
		this.view.createView(message);
	}
	

	handleCardClick(evt) {
		let id = evt.currentTarget.data_messageid;
		console.log('modal ' + id + ' clicked');
		app.view.createMessageModal(id);
	}

	handleAddCardClick(id) {
		console.log('modal - Add New - clicked');
		this.view.createAddMessageModal(id);
	}

	handleSaveMessageClick(id) {
		console.log('Message Save clicked');

		let authorid = document.getElementById("authorid").value;
		let message = document.getElementById("message").value;

        this.model.addMessage(id, authorid, message);
	}

	handleMessageAdded(data) {
		console.log(data);

		this.model.getMessageData();		

		$('#messageModal').modal('toggle');
	}

	handleShowAlert(data) {
		this.view.createAlert(data);
	}


	handleDeleteCard(id) {
		console.log('modal ' + id + ' delete');
		this.model.deleteMessage(id);
	}

	handleMessageDeleted(data) {
		console.log(data);

		this.model.getMessageData();		

		$('#messageModal').modal('toggle');
	}

	handleOpenEditCard(id) {
		
		console.log('modal - Edit ' + id + ' - clicked');
		this.view.createEditMessageModal(id);	
	}


}


const app = new MessageController(new MessageModel(), new MessageView());
