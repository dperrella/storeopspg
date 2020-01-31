
function setCookie(cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  var expires = "expires="+ d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}


var userLang = navigator.language || navigator.userLanguage;
var lang = userLang.toUpperCase();




if(lang == "ES" || lang == "ES-ES"){
	//document.getElementById("userpass").innerHTML = "Introduzca su nombre de usuario y contrase&#241;a";
	//document.getElementById("enter").innerHTML = "Entra";
	//document.getElementById("dimenticata").innerHTML = "He olvidado la contrase&#241;a?";
	
	//document.getElementById("cookieChoiceDismiss").innerHTML = "ACEPTABLE";
	//document.getElementById("moreinfo").innerHTML = "+INFORMACION";
	//document.getElementById("spanText").innerHTML = "Este sitio utiliza cookies para mejorar los servicios y la experiencia del usuario. Si decide continuar navegando consideramos que acepta su uso.";
	(function(window) {
		  if (!!window.cookieChoices) {
		    return window.cookieChoices;
		  }
		  var document = window.document;  
		  var supportsTextContent = 'textContent' in document.body;
		  var cookieChoices = (function() {
		    var cookieName = 'displayCookieConsent';
		    var cookieConsentId = 'cookieChoiceInfo';
		    var dismissLinkId = 'cookieChoiceDismiss';
		    function _createHeaderElement(cookieText, dismissText, linkText, linkHref) {
		      var butterBarStyles = 'position:fixed;width:100%;background-color:#5e6670;' +
		          'margin:0; left:0; top:0;padding:4px;z-index:1000;text-align:center; color:#fff;';
		      var cookieConsentElement = document.createElement('div');
		      cookieConsentElement.id = cookieConsentId;
		      cookieConsentElement.style.cssText = butterBarStyles;
		      cookieConsentElement.appendChild(_createConsentText(cookieText));
		      if (!!linkText && !!linkHref) {
		        cookieConsentElement.appendChild(_createInformationLink(linkText, linkHref));
		      }
		      cookieConsentElement.appendChild(_createDismissLink(dismissText));
		      return cookieConsentElement;
		    }
		    function _createDialogElement(cookieText, dismissText, linkText, linkHref) {
		      var glassStyle = 'position:fixed;width:100%;height:100%;z-index:999;' +
		          'top:0;left:0;opacity:0.5;filter:alpha(opacity=90); color:#fff;' +
		          'background-color:#ccc;';
		      var dialogStyle = 'z-index:1000;position:fixed;left:50%;top:50%';
		      var contentStyle = 'position:relative;left:-50%;margin-top:-25%;' +
		          'background-color:#888;padding:20px;box-shadow:4px 4px 25px #888; color:#fff;';
		      var cookieConsentElement = document.createElement('div');
		      cookieConsentElement.id = cookieConsentId;
		      var glassPanel = document.createElement('div');
		      glassPanel.style.cssText = glassStyle;
		      var content = document.createElement('div');
		      content.style.cssText = contentStyle;
		      var dialog = document.createElement('div');
		      dialog.style.cssText = dialogStyle;
		      var dismissLink = _createDismissLink(dismissText);
		      dismissLink.style.display = 'block';
		      dismissLink.style.textAlign = 'right';
		      dismissLink.style.marginTop = '8px';
		      content.appendChild(_createConsentText(cookieText));
		      if (!!linkText && !!linkHref) {
		        content.appendChild(_createInformationLink(linkText, linkHref));
		      }
		      content.appendChild(dismissLink);
		      dialog.appendChild(content);
		      cookieConsentElement.appendChild(glassPanel);
		      cookieConsentElement.appendChild(dialog);
		      return cookieConsentElement;
		    }
		    function _setElementText(element, text) {
		      if (supportsTextContent) {
		        element.textContent = text;
		      } else {
		        element.innerText = text;
		      }
		    }
		    function _createConsentText(cookieText) {
		      var consentText = document.createElement('span');
		      consentText.id = "spanText";
		      _setElementText(consentText, cookieText);
		      return consentText;
		    }
		    function _createDismissLink(dismissText) {
		      var dismissLink = document.createElement('a');
		      _setElementText(dismissLink, dismissText);
		      dismissLink.id = dismissLinkId;
		      dismissLink.href = '#';
		      //dismissLink.style.marginLeft = '24px';
		      dismissLink.style.marginLeft = '10px';
		      dismissLink.style.color = '#fff';
		      return dismissLink;
		    }
		    function _createInformationLink(linkText, linkHref) {
		      var infoLink = document.createElement('a');
		      _setElementText(infoLink, linkText);
		      infoLink.href = linkHref;
		      infoLink.id = "moreinfo";
		      infoLink.target = '_blank';
		      //infoLink.style.marginLeft = '8px';
		      infoLink.style.marginLeft = '20px';
		      infoLink.style.color = '#fff';
		      //infoLink.style.backgroundColor = "red";
		      return infoLink;
		    }
		    function _dismissLinkClick() {
		      _saveUserPreference();
		      _removeCookieConsent();
		      return false;
		    }
		    function _showCookieConsent(cookieText, dismissText, linkText, linkHref, isDialog) {
		      if (_shouldDisplayConsent()) {
		        _removeCookieConsent();
		        var consentElement = (isDialog) ?
		            _createDialogElement(cookieText, dismissText, linkText, linkHref) :
		            _createHeaderElement(cookieText, dismissText, linkText, linkHref);
		        var fragment = document.createDocumentFragment();
		        fragment.appendChild(consentElement);
		        document.body.appendChild(fragment.cloneNode(true));
		        document.getElementById(dismissLinkId).onclick = _dismissLinkClick;
		      }
		    }
		    function showCookieConsentBar(cookieText, dismissText, linkText, linkHref) {
		      _showCookieConsent(cookieText, dismissText, linkText, linkHref, false);
		    }
		    function showCookieConsentDialog(cookieText, dismissText, linkText, linkHref) {
		      _showCookieConsent(cookieText, dismissText, linkText, linkHref, true);
		    }
		    function _removeCookieConsent() {
		      var cookieChoiceElement = document.getElementById(cookieConsentId);
		      if (cookieChoiceElement != null) {
		        cookieChoiceElement.parentNode.removeChild(cookieChoiceElement);
		      }
		    }
		    function _saveUserPreference() {
		      // Durata del cookie di un anno
		      var expiryDate = new Date();
		      expiryDate.setFullYear(expiryDate.getFullYear() + 1);
		      document.cookie = cookieName + '=y; expires=' + expiryDate.toGMTString();
		    }
		    function _shouldDisplayConsent() {
		      // Per mostrare il banner solo in mancanza del cookie
		      return !document.cookie.match(new RegExp(cookieName + '=([^;]+)'));
		    }
		    var exports = {};
		    exports.showCookieConsentBar = showCookieConsentBar;
		    exports.showCookieConsentDialog = showCookieConsentDialog;
		    return exports;
		  })();
		  window.cookieChoices = cookieChoices;
		  return cookieChoices;
		})(this);
		document.addEventListener('DOMContentLoaded', function(event) {

		    cookieChoices.showCookieConsentBar('Este sitio utiliza cookies para mejorar los servicios y la experiencia del usuario. Si decide continuar navegando consideramos que acepta su uso.',
		        'ACEPTABLE', '+INFORMACION', 'coockiepolicy/coockiepolicy.html');
		    
		  });
}else if(lang == "IT" || lang == "IT-IT"){
	(function(window) {
		  if (!!window.cookieChoices) {
		    return window.cookieChoices;
		  }
		  var document = window.document;  
		  var supportsTextContent = 'textContent' in document.body;
		  var cookieChoices = (function() {
		    var cookieName = 'displayCookieConsent';
		    var cookieConsentId = 'cookieChoiceInfo';
		    var dismissLinkId = 'cookieChoiceDismiss';
		    function _createHeaderElement(cookieText, dismissText, linkText, linkHref) {
		      var butterBarStyles = 'position:fixed;width:100%;background-color:#888;' +
		          'margin:0; left:0; top:0;padding:4px;z-index:1000;text-align:center; color:#fff;';
		      var cookieConsentElement = document.createElement('div');
		      cookieConsentElement.id = cookieConsentId;
		      cookieConsentElement.style.cssText = butterBarStyles;
		      cookieConsentElement.appendChild(_createConsentText(cookieText));
		      if (!!linkText && !!linkHref) {
		        cookieConsentElement.appendChild(_createInformationLink(linkText, linkHref));
		      }
		      cookieConsentElement.appendChild(_createDismissLink(dismissText));
		      return cookieConsentElement;
		    }
		    function _createDialogElement(cookieText, dismissText, linkText, linkHref) {
		      var glassStyle = 'position:fixed;width:100%;height:100%;z-index:999;' +
		          'top:0;left:0;opacity:0.5;filter:alpha(opacity=90); color:#fff;' +
		          'background-color:#ccc;';
		      var dialogStyle = 'z-index:1000;position:fixed;left:50%;top:50%';
		      var contentStyle = 'position:relative;left:-50%;margin-top:-25%;' +
		          'background-color:#888;padding:20px;box-shadow:4px 4px 25px #888; color:#fff;';
		      var cookieConsentElement = document.createElement('div');
		      cookieConsentElement.id = cookieConsentId;
		      var glassPanel = document.createElement('div');
		      glassPanel.style.cssText = glassStyle;
		      var content = document.createElement('div');
		      content.style.cssText = contentStyle;
		      var dialog = document.createElement('div');
		      dialog.style.cssText = dialogStyle;
		      var dismissLink = _createDismissLink(dismissText);
		      dismissLink.style.display = 'block';
		      dismissLink.style.textAlign = 'right';
		      dismissLink.style.marginTop = '8px';
		      content.appendChild(_createConsentText(cookieText));
		      if (!!linkText && !!linkHref) {
		        content.appendChild(_createInformationLink(linkText, linkHref));
		      }
		      content.appendChild(dismissLink);
		      dialog.appendChild(content);
		      cookieConsentElement.appendChild(glassPanel);
		      cookieConsentElement.appendChild(dialog);
		      return cookieConsentElement;
		    }
		    function _setElementText(element, text) {
		      if (supportsTextContent) {
		        element.textContent = text;
		      } else {
		        element.innerText = text;
		      }
		    }
		    function _createConsentText(cookieText) {
		      var consentText = document.createElement('span');
		      consentText.id = "spanText";
		      _setElementText(consentText, cookieText);
		      return consentText;
		    }
		    function _createDismissLink(dismissText) {
		      var dismissLink = document.createElement('a');
		      _setElementText(dismissLink, dismissText);
		      dismissLink.id = dismissLinkId;
		      dismissLink.href = '#';
		      //dismissLink.style.marginLeft = '24px';
		      dismissLink.style.marginLeft = '10px';
		      dismissLink.style.color = '#fff';
		      return dismissLink;
		    }
		    function _createInformationLink(linkText, linkHref) {
		      var infoLink = document.createElement('a');
		      _setElementText(infoLink, linkText);
		      infoLink.href = linkHref;
		      infoLink.id = "moreinfo";
		      infoLink.target = '_blank';
		      //infoLink.style.marginLeft = '8px';
		      infoLink.style.marginLeft = '20px';
		      infoLink.style.color = '#fff';
		      //infoLink.style.backgroundColor = "red";
		      return infoLink;
		    }
		    function _dismissLinkClick() {
		      _saveUserPreference();
		      _removeCookieConsent();
		      return false;
		    }
		    function _showCookieConsent(cookieText, dismissText, linkText, linkHref, isDialog) {
		      if (_shouldDisplayConsent()) {
		        _removeCookieConsent();
		        var consentElement = (isDialog) ?
		            _createDialogElement(cookieText, dismissText, linkText, linkHref) :
		            _createHeaderElement(cookieText, dismissText, linkText, linkHref);
		        var fragment = document.createDocumentFragment();
		        fragment.appendChild(consentElement);
		        document.body.appendChild(fragment.cloneNode(true));
		        document.getElementById(dismissLinkId).onclick = _dismissLinkClick;
		      }
		    }
		    function showCookieConsentBar(cookieText, dismissText, linkText, linkHref) {
		      _showCookieConsent(cookieText, dismissText, linkText, linkHref, false);
		    }
		    function showCookieConsentDialog(cookieText, dismissText, linkText, linkHref) {
		      _showCookieConsent(cookieText, dismissText, linkText, linkHref, true);
		    }
		    function _removeCookieConsent() {
		      var cookieChoiceElement = document.getElementById(cookieConsentId);
		      if (cookieChoiceElement != null) {
		        cookieChoiceElement.parentNode.removeChild(cookieChoiceElement);
		      }
		    }
		    function _saveUserPreference() {
		      // Durata del cookie di un anno
		      var expiryDate = new Date();
		      expiryDate.setFullYear(expiryDate.getFullYear() + 1);
		      document.cookie = cookieName + '=y; expires=' + expiryDate.toGMTString();
		    }
		    function _shouldDisplayConsent() {
		      // Per mostrare il banner solo in mancanza del cookie
		      return !document.cookie.match(new RegExp(cookieName + '=([^;]+)'));
		    }
		    var exports = {};
		    exports.showCookieConsentBar = showCookieConsentBar;
		    exports.showCookieConsentDialog = showCookieConsentDialog;
		    return exports;
		  })();
		  window.cookieChoices = cookieChoices;
		  return cookieChoices;
		})(this);
		document.addEventListener('DOMContentLoaded', function(event) {

		    cookieChoices.showCookieConsentBar('Questo sito utilizza i cookie per migliorare servizi ed esperienza degli utenti. Se decidi di continuare la navigazione consideriamo che accetti il loro uso.',
		        'ACCETTO', '+INFORMAZIONI', 'coockiepolicy/coockiepolicy.html');
		    
		  });
//} (lang == "EN" || lang == "EN-US"){
	} else {
	//document.getElementById("userpass").innerHTML = "Enter username and password";
	//document.getElementById("enter").innerHTML = "Join";
	//document.getElementById("dimenticata").innerHTML = "Forgot password?";
	
	
	//document.getElementById("cookieChoiceDismiss").innerHTML = "I ACCEPT";
	//document.getElementById("moreinfo").innerHTML = "+INFORMATION";
	//document.getElementById("spanText").innerHTML = "This site uses cookies to enhance users' services and experience. If you decide to continue the navigation, consider that you accept their use.";
(function(window) {
  if (!!window.cookieChoices) {
    return window.cookieChoices;
  }
  var document = window.document;  
  var supportsTextContent = 'textContent' in document.body;
  var cookieChoices = (function() {
    var cookieName = 'displayCookieConsent';
    var cookieConsentId = 'cookieChoiceInfo';
    var dismissLinkId = 'cookieChoiceDismiss';
    function _createHeaderElement(cookieText, dismissText, linkText, linkHref) {
      var butterBarStyles = 'position:fixed;width:100%;background-color:#5e6670;' +
          'margin:0; left:0; top:0;padding:4px;z-index:1000;text-align:center; color:#fff;';
      var cookieConsentElement = document.createElement('div');
      cookieConsentElement.id = cookieConsentId;
      cookieConsentElement.style.cssText = butterBarStyles;
      cookieConsentElement.appendChild(_createConsentText(cookieText));
      if (!!linkText && !!linkHref) {
        cookieConsentElement.appendChild(_createInformationLink(linkText, linkHref));
      }
      cookieConsentElement.appendChild(_createDismissLink(dismissText));
      return cookieConsentElement;
    }
    function _createDialogElement(cookieText, dismissText, linkText, linkHref) {
      var glassStyle = 'position:fixed;width:100%;height:100%;z-index:999;' +
          'top:0;left:0;opacity:0.5;filter:alpha(opacity=90);' +
          'background-color:#ccc; color:#fff;';
      var dialogStyle = 'z-index:1000;position:fixed;left:50%;top:50%';
      var contentStyle = 'position:relative;left:-50%;margin-top:-25%;' +
          'background-color:#888;padding:20px;box-shadow:4px 4px 25px #888; color:#fff;';
      var cookieConsentElement = document.createElement('div');
      cookieConsentElement.id = cookieConsentId;
      var glassPanel = document.createElement('div');
      glassPanel.style.cssText = glassStyle;
      var content = document.createElement('div');
      content.style.cssText = contentStyle;
      var dialog = document.createElement('div');
      dialog.style.cssText = dialogStyle;
      var dismissLink = _createDismissLink(dismissText);
      dismissLink.style.display = 'block';
      dismissLink.style.textAlign = 'right';
      dismissLink.style.marginTop = '8px';
      content.appendChild(_createConsentText(cookieText));
      if (!!linkText && !!linkHref) {
        content.appendChild(_createInformationLink(linkText, linkHref));
      }
      content.appendChild(dismissLink);
      dialog.appendChild(content);
      cookieConsentElement.appendChild(glassPanel);
      cookieConsentElement.appendChild(dialog);
      return cookieConsentElement;
    }
    function _setElementText(element, text) {
      if (supportsTextContent) {
        element.textContent = text;
      } else {
        element.innerText = text;
      }
    }
    function _createConsentText(cookieText) {
      var consentText = document.createElement('span');
      consentText.id = "spanText";
      _setElementText(consentText, cookieText);
      return consentText;
    }
    function _createDismissLink(dismissText) {
      var dismissLink = document.createElement('a');
      _setElementText(dismissLink, dismissText);
      dismissLink.id = dismissLinkId;
      dismissLink.href = '#';
      //dismissLink.style.marginLeft = '24px';
      dismissLink.style.marginLeft = '10px';
      dismissLink.style.color = '#fff';
      return dismissLink;
    }
    function _createInformationLink(linkText, linkHref) {
      var infoLink = document.createElement('a');
      _setElementText(infoLink, linkText);
      infoLink.href = linkHref;
      infoLink.id = "moreinfo";
      infoLink.target = '_blank';
      //infoLink.style.marginLeft = '8px';
      infoLink.style.marginLeft = '20px';
      infoLink.style.color = '#fff';
      //infoLink.style.backgroundColor = "red";
      return infoLink;
    }
    function _dismissLinkClick() {
      _saveUserPreference();
      _removeCookieConsent();
      return false;
    }
    function _showCookieConsent(cookieText, dismissText, linkText, linkHref, isDialog) {
      if (_shouldDisplayConsent()) {
        _removeCookieConsent();
        var consentElement = (isDialog) ?
            _createDialogElement(cookieText, dismissText, linkText, linkHref) :
            _createHeaderElement(cookieText, dismissText, linkText, linkHref);
        var fragment = document.createDocumentFragment();
        fragment.appendChild(consentElement);
        document.body.appendChild(fragment.cloneNode(true));
        document.getElementById(dismissLinkId).onclick = _dismissLinkClick;
      }
    }
    function showCookieConsentBar(cookieText, dismissText, linkText, linkHref) {
      _showCookieConsent(cookieText, dismissText, linkText, linkHref, false);
    }
    function showCookieConsentDialog(cookieText, dismissText, linkText, linkHref) {
      _showCookieConsent(cookieText, dismissText, linkText, linkHref, true);
    }
    function _removeCookieConsent() {
      var cookieChoiceElement = document.getElementById(cookieConsentId);
      if (cookieChoiceElement != null) {
        cookieChoiceElement.parentNode.removeChild(cookieChoiceElement);
      }
    }
    function _saveUserPreference() {
      // Durata del cookie di un anno
      var expiryDate = new Date();
      expiryDate.setFullYear(expiryDate.getFullYear() + 1);
      document.cookie = cookieName + '=y; expires=' + expiryDate.toGMTString();
    }
    function _shouldDisplayConsent() {
      // Per mostrare il banner solo in mancanza del cookie
      return !document.cookie.match(new RegExp(cookieName + '=([^;]+)'));
    }
    var exports = {};
    exports.showCookieConsentBar = showCookieConsentBar;
    exports.showCookieConsentDialog = showCookieConsentDialog;
    return exports;
  })();
  window.cookieChoices = cookieChoices;
  return cookieChoices;
})(this);
document.addEventListener('DOMContentLoaded', function(event) {

    cookieChoices.showCookieConsentBar('This site uses cookies to enhance users services and experience. If you decide to continue the navigation, consider that you accept their use.',
        'I ACCEPT', '+INFORMATION', 'coockiepolicy/coockiepolicy.html');
    
  });	
}