import os
from openai import OpenAI

client = OpenAI(
    base_url=os.getenv("API_BASE_URL"),
    api_key=os.getenv("HF_TOKEN")
)

response = client.chat.completions.create(
    model=os.getenv("MODEL_NAME"),
    messages=[{
        "role": "user",
        "content": 'Return this exact JSON with no extra text: {"ranked_candidates": ["c01", "c02"]}'
    }],
    temperature=0.0,
)

print(repr(response.choices[0].message.content))