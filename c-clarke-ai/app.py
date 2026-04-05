
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
        You are a Senior Technical Recruiter. Your task is to perform a strict Skill Gap Analysis.

        COMPARE:
        Job Description: {job_text[:1000]}
        Candidate CV: {cv_text[:3000]}

        SCORING RULES (BE VERY STRICT):
        1. 85-100%: Perfect match. Candidate has the exact skills, tools, and required years of experience.
        2. 50-84%: Partial match. Has the right background but missing 1 or 2 secondary tools or slightly less experience.
        3. 10-49%: Weak match. Right industry (e.g., IT) but wrong specialized skills (e.g., a QA applying for a Dev role).
        4. 0-9%: Complete mismatch. The candidate is from a completely different profession (e.g., a Video Editor applying for a Java role).

        OUTPUT RULES:
        - Return ONLY a JSON object.
        - Every value MUST be a single string (NO lists, NO brackets []).
        - If the score is below 20%, the 'reason_select' should be "Not recommended."

        REQUIRED JSON FORMAT:
        {{
          "match_score": 0,
          "summary": "2-sentence professional overview.",
          "email": "extracted_email",
          "phone": "extracted_phone",
          "education": "Degree and University only",
          "skills": "List top skills separated by commas",
          "work_experience": "Most recent Job Title and Company",
          "reason_select": "Why they fit (or 'None' if low score)",
          "reason_caution": "The biggest reason why they are NOT a fit"
        }}
    """

    try:
        completion = client.chat.completions.create(

            model="google/gemini-2.0-flash-001",
            messages=[
                {"role": "system",
                 "content": "You are a factual HR assistant. If a skill is not in the CV, do NOT list it. Do not invent technical skills for non-technical people."},
                {"role": "user", "content": prompt}
            ],
            temperature=0.0,
            response_format={"type": "json_object"}
        )

        raw_text = completion.choices[0].message.content
        print(f"DEBUG - AI Sent: {raw_text}")
        ai_data = json.loads(raw_text)

        for key in ai_data:
            if isinstance(ai_data[key], (list, dict)):
                ai_data[key] = str(ai_data[key]).replace("[", "").replace("]", "").replace("'", "")

        return jsonify(ai_data)

    except Exception as e:
        print(f"Model Error: {e}")
        return jsonify({"match_score": 0, "summary": "API Connection Error. Please check model ID."}), 200


if __name__ == '__main__':
    app.run(port=5000)
