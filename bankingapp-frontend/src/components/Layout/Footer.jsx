// src/components/Footer.js
import React from 'react';
import { Container, Typography } from '@mui/material';
import './Footer.css'; // Import custom CSS

const Footer = () => {
    return (
        <footer className="footer">
            <Container>
                <Typography variant="body1" align="center">
                    &copy; 2024 BankingApp. All rights reserved.
                </Typography>
            </Container>
        </footer>
    );
};

export default Footer;
