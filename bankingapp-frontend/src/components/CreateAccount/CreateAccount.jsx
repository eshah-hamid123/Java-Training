// src/components/CreateAccount.js
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CreateAccount = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        address: '',
        balance: '',
        accountNumber: ''
    });
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const response = await axios.post('http://localhost:8080/accounts/create-account', formData);
            alert('Account created successfully!');
            setFormData({
                username: '',
                password: '',
                email: '',
                address: '',
                balance: '',
                accountNumber: ''
            });
            navigate('/manage-users');
        } catch (error) {
            
            if (error.response && error.response.status === 409) {
                
                setError(error.response.data);
            } else {
                console.error('There was an error creating the account!', error);
                setError('Error creating account');
            }
        }
    };

    return (
        <div>
            <h2>Create Account</h2>
            {error && <div style={{ color: 'red' }}>{error}</div>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username</label>
                    <input type="text" name="username" value={formData.username} onChange={handleChange} required />
                </div>
                <div>
                    <label>Password</label>
                    <input type="password" name="password" value={formData.password} onChange={handleChange} required minLength="6" />
                </div>
                <div>
                    <label>Email</label>
                    <input type="email" name="email" value={formData.email} onChange={handleChange} required />
                </div>
                <div>
                    <label>Address</label>
                    <input type="text" name="address" value={formData.address} onChange={handleChange} />
                </div>
                <div>
                    <label>Balance</label>
                    <input type="number" name="balance" value={formData.balance} onChange={handleChange} />
                </div>
                <div>
                    <label>Account Number</label>
                    <input type="text" name="accountNumber" value={formData.accountNumber} onChange={handleChange} required minLength="8" />
                </div>
                <button type="submit">Create Account</button>
            </form>
        </div>
    );
};

export default CreateAccount;
