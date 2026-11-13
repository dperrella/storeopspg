<html>
<head>
<title>test</title>
</head>
<body>

<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.14.3/firebase-database.js"></script>

<script>

//Your web app's Firebase configuration
var firebaseConfig = {
  apiKey: "AIzaSyCMslIC8kpABVxT6aNCvFlzt4BUN70hCDU",
  authDomain: "rnprj-a2be7.firebaseapp.com",
  databaseURL: "https://rnprj-a2be7.firebaseio.com",
  projectId: "rnprj-a2be7",
  storageBucket: "rnprj-a2be7.appspot.com",
  messagingSenderId: "845204873852",
  appId: "1:845204873852:web:1693d4cf07826cc8a01ff9"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

var database = firebase.database();	

try {
    mRef.off('value');
}
catch(err) {
    console.log(err);
}
  
mRef = database.ref("occupancy");
mRef.orderByKey().startAt("4769-").endAt('4769\uf8ff').on('value', (snapshot) => {
	  let datas = snapshot.val();
	  
    console.log(JSON.stringify(datas));
    //parseData(datas)
});	

</script> 

</body>
</html>