from fastapi import FastAPI, Body
import requests
from bs4 import BeautifulSoup

app = FastAPI()

@app.post("/api/v1/preview")
def general_preview(payload: dict = Body(...)):
    url = payload.get("url")

    headers = {
        "User-Agent": "facebookexternalhit/1.1 (+http://www.facebook.com/externalhit_uatext.php)"
    }

    try:
        res = requests.get(url, headers=headers, timeout=10)
        res.raise_for_status()
    except requests.RequestException:
        return {"title": None, "thumbnailUrl": None}

    soup = BeautifulSoup(res.text, "html.parser")

    og_title = soup.find("meta", property="og:title")
    og_image = soup.find("meta", property="og:image")

    return {
        "title": og_title["content"] if og_title else None,
        "thumbnailUrl": og_image["content"] if og_image else None
    }
