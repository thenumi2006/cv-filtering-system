from flask import Flask, request, jsonify
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # Allows Spring Boot to talk to Python without security blocks


@app.route('/match', methods=['POST'])
def match_cv():
    data = request.json
    cv_text = data.get('cv_text', '')
    job_description = data.get('job_description', '')

    if not cv_text or not job_description:
        return jsonify({"match_score": 0.0, "error": "Missing text"}), 400

    # NLP Logic: Convert text to numbers (Vectors)
    documents = [cv_text, job_description]
    vectorizer = TfidfVectorizer(stop_words='english')
    tfidf_matrix = vectorizer.fit_transform(documents)

    # Calculate how similar the two vectors are
    # Result is between 0 (no match) and 1 (perfect match)
    score = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:2])[0][0]

    match_percentage = round(float(score) * 100, 2)

    return jsonify({
        "match_score": match_percentage
    })


if __name__ == '__main__':
    print("AI Module is running on http://localhost:5000")
    app.run(port=5000)