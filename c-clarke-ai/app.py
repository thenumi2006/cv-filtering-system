import json
from flask import Flask, request, jsonify
from flask_cors import CORS
from openai import OpenAI

app = Flask(__name__)
CORS(app)

client = OpenAI(
    base_url="https://openrouter.ai/api/v1",
    api_key="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",
)


@app.route('/match', methods=['POST'])
def match_cv():
    data = request.json
    cv_text = data.get('cv_text', '')
    job_text = data.get('job_description', '')

    prompt = f"""
        Analyze this CV against the Job Description. 
        Return ONLY a JSON object with these EXACT keys:
        {{
          "match_score": 85,
          "summary": "Short overview",
          "email": "candidate@example.com",
          "phone": "+123456",
          "education": "BSc IT, University of Moratuwa",
          "skills": "Flutter, Dart, Firebase",
          "work_experience": "Junior Developer at AppWave (2024-Present)",
          "reason_select": "Strong Flutter skills",
          "reason_caution": "Missing cloud experience"
        }}

        CV: {cv_text[:2500]}
        Job: {job_text[:1000]}
        """

    try:
        completion = client.chat.completions.create(
            model="qwen/qwen2.5-coder-7b-instruct",
            messages=[
                {"role": "system", "content": "You are a JSON generator. Use only double quotes."},
                {"role": "user", "content": prompt}
            ],

            response_format={"type": "json_object"}
        )

        raw_text = completion.choices[0].message.content
        print(f"AI Sent: {raw_text}")

        ai_data = json.loads(raw_text)

        return jsonify(ai_data)

    except Exception as e:
        print(f"Error: {e}")
        return jsonify({
            "match_score": 0,
            "summary": "The AI sent a bad format. Please try again.",
            "reason_select": "N/A",
            "reason_caution": str(e)
        }), 200


if __name__ == '__main__':
    app.run(port=5000)