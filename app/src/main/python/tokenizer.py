
from sentence_transformers import SentenceTransformer
import os
from com.chaquo.python import Python
import pickle
context = Python.getPlatform().getApplication()

model_filename = 'my_model'

def get_model_directory(context):
    # Lấy đường dẫn đến thư mục models của ứng dụng
    model_directory = str(Python.getPlatform().getApplication().getFilesDir())
    return model_directory

def save_model(model, model_filename, context):
    # Lưu model vào thư mục model
    model_directory = get_model_directory(context)
    model_path = os.path.join(model_directory, model_filename)

    with open(model_path, 'wb') as model_file:
        pickle.dump(model, model_file)

def load_model(model_filename, context):
    # Nạp model từ thư mục model
    model_directory = get_model_directory(context)
    model_path = os.path.join(model_directory, model_filename)

    with open(model_path, 'rb') as model_file:
        model = pickle.load(model_file)
    return model

if not os.path.exists(os.path.join(get_model_directory(context), model_filename)):
    # Tải và lưu model nếu chưa có
    model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')
    save_model(model, model_filename, context)



def main(sentences):
    model = load_model(model_filename, context)
    embeddings = model.encode(sentences)
    return embeddings
