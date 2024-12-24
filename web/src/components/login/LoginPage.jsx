import  { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiBaseUrl } from '../../config';
import axios from 'axios';

const GoogleSignIn = () => {
  const navigate = useNavigate();
  const [isAuthenticating, setIsAuthenticating] = useState(false);


  useEffect(() => {
    // Check if there's a code in the URL
    const params = new URLSearchParams(window.location.search);
    const code = params.get('code');

    if (code) {
      console.log(code);
      setIsAuthenticating(true); // Show the waiting page
      // Send the code to your backend for verification
      const fetchToken = async () =>{
        console.log("helloooooooooo");
        const api = `${apiBaseUrl}/auth/exchangeToken`;
        const res = await axios.post(api,{},{
          headers: {
            gid: code,
          }
        });
        if(res.status === 201) {
          const accessToken = res.data.data.token.accessToken;
          const refreshToken = res.data.data.token.refreshToken;
          const role = res.data.data.role;
          const userImage = res.data.data.userImage;
          if(role === 'CUSTOMER') {
            localStorage.setItem("accessTokenCustomer",accessToken);
          } else if(role === 'EMPLOYEE') {
            localStorage.setItem("accessTokenEmployee",accessToken);
          } else if(role === 'ADMIN') {
            localStorage.setItem("accessTokenAdmin",accessToken);
          }
          localStorage.setItem("role",role.toLowerCase());
          localStorage.setItem("refreshToken",refreshToken);
          localStorage.setItem("userImage",userImage);
          if(role === "CUSTOMER") {
            {res.data.data.isActive ? navigate("/afterlogin") : navigate*("/enter/customer")}
          } else if (role === "EMPLOYEE") {
            res.data.data.isActive ? navigate("/afterlogin") : navigate("/enter/employee");

          }
          navigate("/afterlogin");
        }
      };
      fetchToken();
    }

  }, []);

  if (isAuthenticating) {
    return (
      <div>
        <h2>Waiting for Authentication...</h2>
        <p>Please wait while we verify your credentials.</p>
      </div>
    );
  }

  return (
    <button >Login with Google</button>
  );
};

export default GoogleSignIn;

/*
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const GoogleSignIn = () => {
  const navigate = useNavigate();
  const [isAuthenticating, setIsAuthenticating] = useState(false);

  const googleAuthUrl = `https://accounts.google.com/o/oauth2/auth/oauthchooseaccount?` +
    `response_type=code` +
    `&client_id=904914536933-rn9iprnahvv77p78n2j7stuiinmjfqp9.apps.googleusercontent.com` +
    `&redirect_uri=${encodeURIComponent('http://localhost:5173/waiting')}` + // Redirect to the waiting page
    `&scope=${encodeURIComponent('openid profile email')}` +
    `&flowName=GeneralOAuthFlow`;

  const handleLogin = () => {
    window.location.href = googleAuthUrl;
  };

  useEffect(() => {
    // Check if there's a code in the URL (on the waiting page)
    const params = new URLSearchParams(window.location.search);
    const code = params.get('code');

    if (code) {
      setIsAuthenticating(true); // Show waiting page
      // Send the code to your backend for verification
      fetch('http://localhost:5000/api/auth/google', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ code }),
      })
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            // Redirect to the intended page after successful authentication
            navigate('/dashboard');
          } else {
            console.error('Authentication failed', data.error);
            navigate('/error'); // Redirect to an error page if needed
          }
        })
        .catch(error => {
          console.error('Error during authentication:', error);
          navigate('/error'); // Redirect to an error page if needed
        });
    }
  }, [navigate]);

  if (isAuthenticating) {
    // Render waiting page
    return (
      <div>
        <h1>Authenticating...</h1>
        <p>Please wait while we verify your login.</p>
      </div>
    );
  }

  // Default login button
  return (
    <button onClick={handleLogin}>Login with Google</button>
  );
};

export default GoogleSignIn;

*/