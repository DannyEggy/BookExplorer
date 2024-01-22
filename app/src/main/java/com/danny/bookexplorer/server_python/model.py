



import tensorflow as tf


from sentence_transformers import SentenceTransformer
sentences = ["This is an example sentence", "Each sentence is converted"]

model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')
# embeddings = model.encode(sentences)
# print(embeddings)

# model = tf.saved_model.load('sentence-transformers/all-MiniLM-L6-v2')

# Convert the model
converter = tf.lite.TFLiteConverter.from_saved_model("tf_model.h5") # path to the SavedModel directory
tflite_model = converter.convert()

# Save the model.
with open('model.tflite', 'wb') as f:
  f.write(tflite_model)