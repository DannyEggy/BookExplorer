from sentence_transformers import SentenceTransformer
# from android.content import Context


def main(sentences):



  model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')
  embeddings = model.encode(sentences)
  return embeddings

print(str(main("Java")))
