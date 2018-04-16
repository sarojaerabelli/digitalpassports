"""Code for generating public and private key pair for RSA."""

from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5 
from Crypto.Hash import SHA256
from base64 import b64encode, b64decode

government_key = RSA.generate(2048, e=65537) 
government_public_key = government_key.publickey().exportKey("PEM") 
government_private_key = government_key.exportKey("PEM")

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

def sign(data):
    rsakey = RSA.importKey(government_private_key) 
    signer = PKCS1_v1_5.new(rsakey) 
    digest = SHA256.new() 
    # It's being assumed the data is base64 encoded, so it's decoded before updating the digest 
    digest.update(b64decode(data)) 
    sign = signer.sign(digest) 
    return b64encode(sign)