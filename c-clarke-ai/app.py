from flask import Flask, request, jsonify
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

app = Flask(__name__)


@app.route('/match', methods=['POST'])
def match_cv():
    # Receives CV text and Job description from Spring Boot [cite: 155]
    data = request.json
    cv_text = data.get('cv_text')
    job_text = data.get('job_description')

    if not cv_text or not job_text:
        return jsonify({"error": "Missing data"}), 400

    # TF-IDF Vectorization for NLP Matching [cite: 20, 155]
    documents = [cv_text, job_text]
    vectorizer = TfidfVectorizer()
    tfidf_matrix = vectorizer.fit_transform(documents)

    # Calculate Cosine Similarity:
    # $similarity = \cos(\theta) = \frac{\mathbf{A} \cdot \mathbf{B}}{\|\mathbf{A}\| \|\mathbf{B}\|}$
    score = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])[0][0]

    return jsonify({"match_score": round(score * 100, 2)})


if __name__ == '__main__':
    app.run(port=5000)  #