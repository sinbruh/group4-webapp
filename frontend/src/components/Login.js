import React from 'react';


function Login(props) {
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');

    function handleLogin(e) {
        e.preventDefault();
        // code to handle login goes here
        props.toggle
    }


}