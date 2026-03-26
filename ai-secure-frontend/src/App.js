import React, { useState } from "react";
import "./App.css";

function App() {
  const [text, setText] = useState("");
  const [result, setResult] = useState(null);
  const [file, setFile] = useState(null);

  const analyzeText = async () => {
    const response = await fetch("http://localhost:8080/analyze/text", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: text
    });

    const data = await response.json();
    setResult(data);
  };

  const analyzeFile = async () => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch("http://localhost:8080/analyze/file", {
      method: "POST",
      body: formData
    });

    const data = await response.json();
    setResult(data);
  };

  return (
    <div className="container">
      <h1>AI Secure Log Analysis</h1>

      <textarea
        placeholder="Paste log text here..."
        value={text}
        onChange={(e) => setText(e.target.value)}
      />

      <button onClick={analyzeText}>Analyze Text</button>

      <br />

      <input type="file" onChange={(e) => setFile(e.target.files[0])} />
      <button onClick={analyzeFile}>Upload Log File</button>

      {result && (
        <div className="result-box">
          <h3>Summary:</h3>
          <p>{result.summary}</p>

          <h3>Risk Score:</h3>
          <p>{result.riskScore}</p>

          <h3>Risk Level:</h3>
          <p className="risk">{result.riskLevel}</p>

          <h3>Findings:</h3>
          <ul>
            {result.findings &&
              result.findings.map((item, index) => (
                <li key={index}>{item}</li>
              ))}
          </ul>

          <h3>Insights:</h3>
          <ul>
            {result.insights &&
              result.insights.map((item, index) => (
                <li key={index}>{item}</li>
              ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default App;