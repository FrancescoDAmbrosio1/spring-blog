
//recupero dal DOM gli elementi del form
const formUtente = document.getElementById('user-form');
const userInputSurname = document.getElementById('userInputSurname');
const userInputName = document.getElementById('userInputName');
const userInputMail = document.getElementById('userInputMail');
const userInputPassword = document.getElementById('userInputPassword');
const userInputProgileImgUrl = document.getElementById('userInputProgileImgUrl');
const errorMessageInputSurname = document.getElementById('errorMessageInputSurname');
const errorMessageInputName = document.getElementById('errorMessageInputName');
const errorMessageInputEmail = document.getElementById('errorMessageInputEmail');
const errorMessageInputPassword = document.getElementById('errorMessageInputPassword');

let emailAlreadyExist = true;


//intercettazione form per validazione
formUtente.addEventListener('submit', userFormValidation);

//funzione di validazione form
function userFormValidation(e){
	
	e.preventDefault()
	
	let areErrorsInForm = false;
	let emailAlreadyExistMessageError = verifyIfEmailAlreadyExist(userInputMail.value);
	
	if(userInputSurname.value.trim().length === 0){
			userInputSurname.classList.add('border');
			errorMessageInputSurname.innerHTML = "Inserimento cognome obbligatorio";
			areErrorsInForm = true
			console.log("sono in userInputSurname.value.trim().length === 0 --- areErrorsInForm: " + areErrorsInForm)
		}else {
			userInputSurname.classList.remove('border');
			errorMessageInputSurname.innerHTML = "";
			areErrorsInForm = false
			console.log("sono in userInputSurname.value.trim().length === 0 ELSE --- areErrorsInForm: " + areErrorsInForm)
	}
	
	if(userInputName.value.trim().length === 0){
			userInputName.classList.add('border');
			errorMessageInputName.innerHTML = "Inserimento nome obbligatorio";
			areErrorsInForm = true
			console.log("sono in userInputName.value.trim().length === 0 --- areErrorsInForm: " + areErrorsInForm)
		}else {
			userInputName.classList.remove('border');
			errorMessageInputName.innerHTML = "";
			areErrorsInForm = false
			console.log("sono in userInputName.value.trim().length === 0 ELSE --- areErrorsInForm: " + areErrorsInForm)
	}
	
	if(userInputMail.value.trim().length === 0){
			userInputMail.classList.add('border');
			errorMessageInputEmail.innerHTML = "Inserimento email obbligatorio";
			areErrorsInForm = true
			console.log("sono in userInputMail.value.trim().length === 0 --- areErrorsInForm: " + areErrorsInForm)
		}else if(emailAlreadyExist == true){
			userInputMail.classList.add('border');
			errorMessageInputEmail.innerHTML = emailAlreadyExistMessageError;
			areErrorsInForm = true	
			console.log("sono in emailAlreadyExist == true --- areErrorsInForm: " + areErrorsInForm)	
		}else  {
			console.log("sono in userInputMail.value.trim().length === 0 ELSE--- areErrorsInForm: " + areErrorsInForm)
			userInputMail.classList.remove('border');
			errorMessageInputEmail.innerHTML = "";
			areErrorsInForm = false
	}
	
	if(userInputPassword.value.trim().length === 0){
				userInputPassword.classList.add('border');
				errorMessageInputPassword.innerHTML = "Inserimento password obbligatorio";
				areErrorsInForm = true
				console.log("sono in userInputPassword.value.trim().length === 0 --- areErrorsInForm: " + areErrorsInForm)
			}else if(isConformityOfPasswordOk(userInputPassword.value) == false){
					userInputPassword.classList.add('border');
					errorMessageInputPassword.innerHTML = "Password non conforme, rispettare i requisiti";
					areErrorsInForm = true
					console.log("sono in isConformityOfPasswordOk(userInputPassword.value) == false --- areErrorsInForm: " + areErrorsInForm)
				} else{	
					userInputPassword.classList.remove('border');
					errorMessageInputPassword.innerHTML = "";
					//areErrorsInForm = false
					console.log("sono in isConformityOfPasswordOk(userInputPassword.value) == false ELSE --- areErrorsInForm: " + areErrorsInForm)
					}
	
		
	if(areErrorsInForm == false){
		console.log("sono al submit-- areErrorsInForm="+areErrorsInForm);
		//formUtente.submit();
	}
}



//funzione che verifica se la mail è gia presente a db
function verifyIfEmailAlreadyExist(mail){
	const api_verifyMailUrl = 'http://localhost:8080/Springblog/api/verify-email-'+mail; 
	
	fetch(api_verifyMailUrl).then(result =>{
		if(!result.ok){
			emailAlreadyExist = true;
			return "Email non valida...riprovare";
			
		}
		if(result == true){
			emailAlreadyExist = true;
			return "Email già registrata";
		}
	});
}

//funzione che verifica la conformità della password
function isConformityOfPasswordOk(password){

	let isConformityOk = true;
	let pswContainsNumbers = false;
	let pswContainsLetters = false;
	let pswContainsSpecialChars = false;
		
		const specialChars = ["@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=", "{", "}", "|", ":", ";", ".", "?", "/", "~"];
		const letters = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "Z", "X", "Y", "Z"];
		const numbers = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
		
	console.log("password inserita: " + password);
	//se la password è >= 12 caratteri vado a verificare tutti gli altri requisiti
	if(password.length >= 12){
		console.log("la password è >= di 12 caratteri")
		
		const passwordSplit = password.split("");
		console.log("password splittata: " + passwordSplit)
		passwordSplit.forEach(item =>{
			if(item == " "){
				//console.log("la password contiene spazi");
				isConformityOk = false
			} else{
				numbers.forEach(number =>{
					if(item === number){
						pswContainsNumbers = true;
						//console.log("la psw, al carattere " + item + " contiene il numero: " + number );
					}
					
				});
				
				specialChars.forEach(specChar =>{
					if(item === specChar){
						pswContainsSpecialChars = true;
						//console.log("la psw, al carattere " + item + " contiene il lo specChar: " + specChar);
					}
				});
				
				letters.forEach(letter =>{
					if(item === letter){
						pswContainsLetters = true;
						//console.log("la psw, al carattere " + item + " contiene il la lettera: " + letter);
					}
				})
			}
			
		});
			
				
		
	}else{ 
		//se la psw è minore di 12 caratteri interrompo 
		//console.log("la password  è < di 12 caratteri");
		isConformityOk = false
		}
		
	//check requisiti psw
	if (pswContainsNumbers == false) {
		//console.log("la psw non contiene numeri");
		isConformityOk = false;
	} else {
		//console.log("la psw contiene almeno un numero")
	}	
	
	if (pswContainsSpecialChars == false) {
			//console.log("la psw non contiene specChars");
			isConformityOk = false;
		} else {
			//console.log("la psw contiene almeno uno specChars")
		}	
	
	if (pswContainsLetters == false) {
			//console.log("la psw non contiene lettere");
			isConformityOk = false;
		} else {
			//console.log("la psw contiene almeno una lettera")
		}	
	
	console.log("isConformityOk: " + isConformityOk)
		
	return isConformityOk;
}
		
		
		