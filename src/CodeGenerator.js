import React, { useState } from "react";

function CodeGenerator() {
  const [description, setDescription] = useState("");
  const [language, setLanguage] = useState("Java");
  const [generatedCode, setGeneratedCode] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(""); // New state for error messages

  const handleGenerateCode = async () => {
    setLoading(true);
    setError(""); // Clear previous errors

    try {
      const response = await fetch("http://localhost:8080/api/generate", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          description: description,
          language: language,
        }),
      });

      if (response.ok) {
        const data = await response.text();
        setGeneratedCode(data);
      } else {
        setError("Error generating code. Please try again.");
      }
    } catch (e) {
      setError("Error connecting to the server. Check your connection.");
    }
    setLoading(false);
  };

  return (
    <div>
      <h1>AI Code Generator</h1>
      <textarea
        rows="4"
        cols="50"
        placeholder="Enter the code description"
        value={description}
        maxLength={500} // Limit the number of characters
        onChange={(e) => setDescription(e.target.value)}
      />
      <br />
      <label htmlFor="language">Choose a language:</label>
      <select
        id="language"
        value={language}
        onChange={(e) => setLanguage(e.target.value)}
      >
        <option value="Java">Java</option>
        <option value="Python">Python</option>
        <option value="JavaScript">JavaScript</option>
        <option value="C++">C++</option>
      </select>
      <br />
      <button onClick={handleGenerateCode} disabled={loading}>
        {loading ? "Generating..." : "Generate Code"}
      </button>
      <h2>Generated Code:</h2>
      {error && <p style={{ color: "red" }}>{error}</p>} {/* Displays error message */}
      <pre>{generatedCode}</pre>
    </div>
  );
}

export default CodeGenerator;
