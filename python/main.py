from fastapi import FastAPI, APIRouter
from pydantic import BaseModel
from bs4 import BeautifulSoup
import requests
from urllib.parse import urljoin

router = APIRouter()

class PreviewRequest(BaseModel):
    url: str

@router.post("/api/v1/preview")
def preview(request: PreviewRequest):
    url = request.url
    response = requests.get(url, headers={"User-Agent": "Mozilla/5.0"})
    soup = BeautifulSoup(response.text, "html.parser")

    # 제목
    og_title = soup.find("meta", property="og:title")
    title = og_title["content"] if og_title and og_title.get("content") else "제목 없음"

    # 썸네일
    og_image = soup.find("meta", property="og:image")
    thumbnail = og_image["content"] if og_image and og_image.get("content") else None

    # 파비콘
    icon_tag = soup.find("link", rel=lambda x: x and "icon" in x.lower())
    favicon_url = icon_tag["href"] if icon_tag and icon_tag.get("href") else None
    if favicon_url and not favicon_url.startswith("http"):
        favicon_url = urljoin(url, favicon_url)

    # 썸네일이 없으면 파비콘으로 대체
    thumbnail_to_use = thumbnail or favicon_url

    return {
        "title": title,
        "thumbnailUrl": thumbnail_to_use,
        "faviconUrl": favicon_url
    }

app = FastAPI()
app.include_router(router)
