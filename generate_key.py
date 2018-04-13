"""Code for generating public and private key pair for RSA."""

from Crypto.PublicKey import RSA

def generate_RSA_keys(bits=2048):
    """Generate RSA private and public key pair with exponent 65537.

    Args:
       bits (int): Key length in bits.

    Returns:
       Tuple of private key and public key PEM files.
    """
    new_key = RSA.generate(bits, e=65537) 
    public_key = new_key.publickey().exportKey("PEM") 
    private_key = new_key.exportKey("PEM") 
    return private_key, public_key