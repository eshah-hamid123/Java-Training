import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/v1/auth/login', {
        username,
        password,
      });

      if (response.status === 200) {
        const { jwtToken, account } = response.data;
        const role = account.role
        localStorage.setItem('token', jwtToken);
        localStorage.setItem('role', role);
        
        if (role === 'admin') {
          navigate('/admin-dashboard');
        } else if (role === 'account-holder') {
          navigate('/account-holder-dashboard', { state: { account } });
        }
      } else {
        setErrorMessage('Login failed');
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setErrorMessage(error.response.data);
      } else {
        alert(error)
        setErrorMessage('An error occurred. Please try again.');
      }
    }
  };

  return (
    <div>
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Login</h2>
        {errorMessage && <p>{errorMessage}</p>}
        <div>
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
