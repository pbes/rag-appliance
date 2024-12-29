import chainlit as cl
import requests
import os

from dotenv import load_dotenv
load_dotenv()

endpoint_url = os.getenv("ENDPOINT_URL")

@cl.on_message
async def main(message: cl.Message):
    message_str = message.content
    response = requests.post(endpoint_url, json = {"message": message_str})

    await cl.Message(
        content=response.text,
    ).send()