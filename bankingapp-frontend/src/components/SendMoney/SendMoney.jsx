import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SendMoney = () => {
    const [formData, setFormData] = useState({
        recieverAccountNumber: '',
        amount: '',
        description: '',
    });
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage('');
        try {
            const token = localStorage.getItem('token');
            const response = await axios.post('http://localhost:8080/transactions/post', formData, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (response.status === 201) {
                navigate('/transaction-success');
            }
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setErrorMessage(error.response.data);
            } else {
                console.log(error)
                setErrorMessage('An error occurred. Please try again.');
            }
        }
    };

    return (
        <div>
            <h2>Send Money</h2>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Receiver Account Number</label>
                    <input
                        type="text"
                        name="recieverAccountNumber"
                        value={formData.recieverAccountNumber}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Amount</label>
                    <input
                        type="number"
                        name="amount"
                        value={formData.amount}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Description</label>
                    <input
                        type="text"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                    />
                </div>
                <button type="submit">Send Money</button>
            </form>
        </div>
    );
};

export default SendMoney;
