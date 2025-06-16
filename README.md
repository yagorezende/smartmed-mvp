# SmartMed MVP Codebase

This repository contains the core components used to test the Minimum Viable Product (MVP) of the **SmartMed** system — a decentralized access control solution for electronic health data.

## Overview

The SmartMed MVP integrates blockchain technology and federated identity to provide secure and auditable access to healthcare data. This codebase includes:

- **Smart Contracts for Ethereum-Based Platforms**
  - Solidity contracts for use with Ethereum and Hyperledger Besu.

- **Chaincode for Hyperledger Fabric**
  - Go-based chaincode designed for the Hyperledger Fabric blockchain platform.

- **SmartMed Keycloak Plugin**
  - A custom Keycloak extension that interfaces with the three supported blockchain platforms (Ethereum, Hyperledger Besu, and Hyperledger Fabric) for decentralized access control enforcement.

- **Protected Django Application**
  - A minimal Django web application that demonstrates authentication, authorization, and protected resource access through the SmartMed system.

## Directory Structure
smartmed-mvp/
├── blockchain/ # genesis block for Hyperledger Besu (ethereum is the same configs)
├── policies/ethereum/ # Solidity smart contracts for Ethereum/Besu
├── policies/fabric/ # Go chaincode for Hyperledger Fabric
├── plugin/ # SmartMed blockchain-aware Keycloak SPI
├── django_app/ # Basic Django app protected by Keycloak
└── README.md

## Getting Started

Each component has its own README with setup instructions. To test the full system:

1. Deploy a Keycloak instance with the SmartMed plugin.
2. Choose and configure a supported blockchain backend (Ethereum, Besu, or Fabric).
3. Deploy the corresponding smart contracts or chaincode.
4. Launch the protected Django app and authenticate through Keycloak.
5. Register the resource for protection on the Authentication tab of the Keycloak client.
    5.1. Resouce name and URL: `/api/v1/logs/`
    5.2. Scope: `read`
6. Request the protected resource from the Django app.
    `GET localhost:8000/api/v1/logs/`

## License

This project is licensed under the GPLv3 License.