from transformers import AutoTokenizer, AutoModel
import torch
import torch.nn.functional as F

#Mean Pooling - Take attention mask into account for correct averaging
def mean_pooling(model_output, attention_mask):
    token_embeddings = model_output[0] #First element of model_output contains all token embeddings
    input_mask_expanded = attention_mask.unsqueeze(-1).expand(token_embeddings.size()).float()
    return torch.sum(token_embeddings * input_mask_expanded, 1) / torch.clamp(input_mask_expanded.sum(1), min=1e-9)


# Sentences we want sentence embeddings for
sentences = "The Queen"

# Load model from HuggingFace Hub
tokenizer = AutoTokenizer.from_pretrained('sentence-transformers/all-MiniLM-L6-v2')
model = AutoModel.from_pretrained('sentence-transformers/all-MiniLM-L6-v2')

# Tokenize sentences
encoded_input = tokenizer(sentences, padding=True, truncation=True, return_tensors='pt')

print("Attendtion_Mask: ")
print(encoded_input['attention_mask']) 

print("Input_Ids: ")
print(encoded_input['input_ids']) 


print("Token_type_ids: ")
print(encoded_input['token_type_ids']) 

# Compute token embeddings
with torch.no_grad():
    model_output = model(**encoded_input)
print("_______________________________________________________________")
    
print(model_output)    

# Perform pooling
sentence_embeddings = mean_pooling(model_output, encoded_input['attention_mask'])
print("_______________________________________________________________")

print(sentence_embeddings)

# Normalize embeddings
sentence_embeddings = F.normalize(sentence_embeddings, p=2, dim=1)

print("_______________________________________________________________")
print(sentence_embeddings)


# print("Sentence embeddings:")
# print(sentence_embeddings)

# Print the input shape
for key, value in encoded_input.items():
    print(f"{key}: {value.shape}")
