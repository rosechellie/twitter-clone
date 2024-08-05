
'use client';

import React, { useState, ChangeEvent, FormEvent } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';

export const LoginForm = () => {

  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string>('');
  const router = useRouter();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError('');

    // try {
    //   const res = await fetch(`http://localhost:8080/login`, {
    //     method: "POST",
    //     headers: {
    //       'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify({ username, password }),
    //     credentials: 'include'
    //   });
    //   console.log(res)
    //   if(res.ok) {
    //     console.log('OK');
    //     router.push('/home');
    //   } else {
    //     console.log('Invalid username or password');
    //     setError('Invalid username or password');
    //   }
    // } catch (error) {
    //   console.log('Error loggin in');
    //   console.log(error);
    //   setError('Error logging in.')
    // }

    try {
      const res = await axios.post(`http://localhost:8080/login`, 
        { username, password },
        { headers: {
          'Content-Type': 'application/json'
        },
          withCredentials: true }
      );
      console.log(res)
      if(res.status === 200) {
        console.log('OK');
        router.push('/home');
      } else {
        console.log('Invalid username or password');
        setError('Invalid username or password');
      }
    } catch (error) {
      console.log('Error loggin in');
      console.log(error);
      setError('Error logging in.')
    }
  }

  return (
    <div>
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit">Login</button>
      </form>
    </div>
  )

}