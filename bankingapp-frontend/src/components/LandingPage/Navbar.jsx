import React from 'react';
import styled from 'styled-components';

const NavbarContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #4f46e5;
  color: black;
`;

const Logo = styled.h1`
  font-size: 1.75rem;
`;

const NavLinks = styled.div`
  a {
    color: white;
    text-decoration: none;
    margin: 0 1rem;
    font-size: 1rem;
    transition: color 0.3s ease;

    &:hover {
      color: #a78bfa;
    }
  }
`;

const Navbar = () => {
  return (
    // <NavbarContainer>
    //   <Logo>BankApp</Logo>
    //   <NavLinks>
    //     <a href="/">Home</a>
    //     <a href="/login">Login</a>
    //     <a href="/signup">Sign Up</a>
    //   </NavLinks>
    // </NavbarContainer>
    <h1>hhhhh</h1>
  );
};

export default Navbar;
