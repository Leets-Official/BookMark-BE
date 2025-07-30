from fastapi import FastAPI, APIRouter, Query, HTTPException
from pydantic import BaseModel
from bs4 import BeautifulSoup
import requests
from urllib.parse import urljoin, urlparse
from fastapi.responses import JSONResponse

router = APIRouter()

class PreviewRequest(BaseModel):
    url: str

@router.get("/api/v1/preview")
def preview(url: str = Query(...)):
    headers_list = [
        {"User-Agent": "facebookexternalhit/1.1"},
        {"User-Agent": "Mozilla/5.0"}
    ]
    response = None
    for headers in headers_list:
        try:
            response = requests.get(url, headers=headers, timeout=5)
            response.raise_for_status()
            break
        except requests.exceptions.RequestException:
            continue

    if response is None:
        return JSONResponse(
            status_code=400,
            content={
                "title": "요청 실패",
                "thumbnailUrl": None,
                "faviconUrl": None,
                "error": "모든 User-Agent 요청 실패"
            }
        )

    soup = BeautifulSoup(response.text, "html.parser")
    parsed_url = urlparse(url)
    hostname = parsed_url.hostname or ""

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
    
    # 플랫폼 이름 추출
    og_site_name = soup.find("meta", property="og:site_name")
    platform = og_site_name["content"] if og_site_name and og_site_name.get("content") else None

    # 예외 처리: 특정 도메인 강제 지정
    if "blog.naver.com" in hostname:
        platform = "네이버 블로그"
    elif "tistory.com" in hostname:
        platform = "티스토리"
    elif"x.com" in hostname:
        platform = "트위터"
    elif platform is None:
        platform = hostname

    # 썸네일이 없으면 파비콘으로 대체
    thumbnail_to_use = thumbnail or favicon_url

    return {
        "title": title,
        "thumbnailUrl": thumbnail_to_use,
        "faviconUrl": favicon_url,
        "platform": platform
    }

app = FastAPI()
app.include_router(router)
