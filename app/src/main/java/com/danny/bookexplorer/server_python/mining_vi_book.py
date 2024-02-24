import requests
import csv
import time

def search_books(query, language='vi'):
    # Thay đổi API_KEY thành API key của bạn
    API_KEY = 'AIzaSyDf90oJZckpJ3w-bfOB6qVw8kmRycNMQbQ'
    url = 'https://www.googleapis.com/books/v1/volumes'

    params = {
        'q': query,
        'langRestrict': language,
        'key': API_KEY
    }

    response = requests.get(url, params=params)

    if response.status_code == 200:
        return response.json()
    else:
        print(f"Error: {response.status_code}")
        return None

def extract_book_data(book_item):
    # Trích xuất thông tin từ một mục sách
    book_id = book_item.get('id', '')
    title = book_item.get('volumeInfo', {}).get('title', '')
    description = book_item.get('volumeInfo', {}).get('description', '')
    authors = book_item.get('volumeInfo', {}).get('authors', [])
    categories = book_item.get('volumeInfo', {}).get('categories', [])
    average_rating = book_item.get('volumeInfo', {}).get('averageRating', '')
    maturity_rating = book_item.get('volumeInfo', {}).get('maturityRating', '')
    published_date = book_item.get('volumeInfo', {}).get('publishedDate', '')
    page_count = book_item.get('volumeInfo', {}).get('pageCount', '')

    return {
        'id': book_id,
        'title': title,
        'description': description,
        'authors': ', '.join(authors),
        'categories': ', '.join(categories),
        'averageRating': average_rating,
        'maturityRating': maturity_rating,
        'publishedDate': published_date,
        'pageCount': page_count
    }

def save_to_csv(data, filename='books.csv'):
    # Lưu dữ liệu vào tệp CSV
    with open(filename, 'a', newline='', encoding='utf-8-sig') as csvfile:
        fieldnames = data[0].keys()
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

        # Kiểm tra xem tệp CSV có trống hay không
        is_empty = csvfile.tell() == 0
        
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

        # Nếu tệp trống, viết header
        if is_empty:
            writer.writeheader()

        # Viết dữ liệu
        writer.writerows(data)

def main():
    # Tìm kiếm sách tiếng Việt với từ khóa "python" (có thể thay đổi thành từ khóa khác)
    
    language_code = 'vi'
    num_books_to_fetch = 200

    books_titles = ["Python", "Đắc Nhân tâm", "Công chúa", "Hoàng tử",
                    "Java", "Tự truyện", "Nhật ký", "Dế mèn", "Chiếc", "Hồn", "Gái", "Trai"
                    ,"hoa", "bóng", "tôi","bạn", "cha", "mẹ", "cậu", "tớ", "ông", "bà", "giấc mơ"
                    , "đam mê", "tiền", "thuốc", "học", "ăn", "đầu", "ta", "chào", "bàn", "tối", "sáng"
                    , "tìm", "sách", "người", "lòng", "tin", "thân", "mãi", "yêu", "em", "anh", "ghế"
                    ,"thương", "hãy", "thông", "minh", "việt", "nam", "nữ", "dữ liệu", "nghề", "bò", "tây", "tay", "chân","chiến"
                    ,"đấu", "đối", "kháng", "nông", "trại", "thủy", "sản", "địa", "cách","núi", "miền", "hồ", "đồng"
                    ,"bánh", "kẹo", "thẻ", "nước", "đất", "tâm", "hồn", "danh", "tài", "hải", "chim", "thọ", "lạnh"
                    ,"nóng", "ngày", "vòng", "phím", "in", "bình", "nắm", "cửa", "sổ", "thiên", "văn", "vẽ", "tranh", "sách"
                    ,"thôi", "chung", "là", "cuộc", "sống","chai", "đầu", "tóc", "tai", "nói", "chuyện", "uống", "vui"
                    , "buồn", "cực", "kho", "hột", "vịt", "ngón", "mang", "mới", "cũ", "nhỏ", "lớn", "ốm", "mập", "mọc", "nâng"
                    ,"hình", "cắn", "cực", "khổ", "may", "xui", "một", "hai", "ba", "bốn", "tứ", "năm", "ngũ", "sáu", "lục"
                    , "bảy", "thất", "tám", "bát", "chín", "chính", "mười", "thập", "mốt", "chết", "tử", "sinh", "trăng", "trời"
                    , "thầy", "cô", "công", "sự", "nghiệp", "vô", "loại", "kinh", "tết", "tế", "thuyết", "mạng", "dụng", "thơ"
                    , "văn", "doanh", "địch","đại", "tạp", "chí", "tiên", "đơn", "mưa", "máu", "ảnh", "ngân", "viễn", "trái"
                    ,"trung", "bắc", "thuộc", "pháp", "lưu", "bị", "triệu", "tử", "long", "trương", "phi", "hoàng", "siêu", "mã",
                    "hổ", "động", "vật", "họa", "đêm", "gia", "cát", "lượng", "khổng", "minh", "tiên", "sinh", "kính", "từ", "thứ"
                    , "lương", "tắc", "lữ", "bố", "điêu", "thuyền", "tào", "tháo", "tam", "quốc", "dân", "nhất", "thống", "thiên"
                    , "thiên", "cổ", "kiên", "hương", "nhận", "bay", "quê", "hỏi", "thăm", "nghiệm", "gợi", "ý", "suy", "ngẫm"
                    , "vần", "gieo", "sầu", "làm", "được", "mới", "xóa", "nha", "coi", "báo", "máu", "lửa", "than", "thở"
                    , "hình", "cần", "email", "tương", "tát", "nhắn", "liềm", "bùn", "buồn", "con", "gan", "gánh", "mở", "ánh", "an"
                    , "thạch", "quân", "thư", "thảo", "khiêm", "thịnh", "phúc", "bảo", "phát", "đạt", "huy", "khôi", "nga", "hải"
                    ,"lan", "phượng", "ngọc", "huệ", "cúc", "xuân", "hạ", "thu", "đông", "hộp", "mập", "ú", "bông", "gòn", "hạn"
                    , "tết", "nghèo", "khổ", "đói", "giặc", "kẻ", "thước", "bút", "cụ", "ông", "bà", "javascript", "in","điện"
                    , "lực", "tá", "tả", "tạ", "ôm"]

    for book in books_titles:

        # Gửi yêu cầu tìm kiếm
        search_results = search_books(book, language_code)

        if search_results:
            # Lấy danh sách các sách từ kết quả tìm kiếm
            books = search_results.get('items', [])[:num_books_to_fetch]

            # Chuẩn bị dữ liệu cho CSV
            books_data = [extract_book_data(book) for book in books]

            # Lưu vào tệp CSV
            save_to_csv(books_data)
            print(f"{num_books_to_fetch} books data saved to 'books.csv'.")
            
            time.sleep(3)

if __name__ == "__main__":
    main()
