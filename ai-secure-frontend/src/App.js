
import React, { useState } from "react";
import "./App.css";

function App() {
  const [text, setText] = useState("");
  const [result, setResult] = useState(null);
  const [file, setFile] = useState(null);

  const analyzeText = async () => {
    const response = await fetch("http://localhost:8080/analyze/text", {
      method: "POST",
      headers: { "Content-Type": "text/plain" },
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

      <br /><br />

      <input type="file" onChange={(e) => setFile(e.target.files[0])} />
      <button onClick={analyzeFile}>Upload Log File</button>

      {result && (
        <div className="result-box">
          <h2>Analysis Result (JSON)</h2>
          <pre>{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default App;