// Import the functions you need from the SDKs you need
import firebase from 'firebase/app';
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getFirestore } from "firebase/firestore";

// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
// const firebaseConfig = {
//   apiKey: "AIzaSyB0wNv809uVtgp3TVtQ4lkDPacUPz41gmw",
//   authDomain: "social-network-former-member.firebaseapp.com",
//   projectId: "social-network-former-member",
//   storageBucket: "social-network-former-member.appspot.com",
//   messagingSenderId: "789110003997",
//   appId: "1:789110003997:web:450180ed2e1cbc1fb8e9f4",
//   measurementId: "G-X4VM6BEVXL"
// };


// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const db = getFirestore(app);
export { db };
