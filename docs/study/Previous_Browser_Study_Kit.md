# Shopper validation kit — use only after prototype smoke-testing

Status: zero participants recruited or observed. The packaged prototype is a browser-based comparison study tool, not an Android APK. A real Android app and Urdu translation remain outstanding. Do not use this study to claim an Android implementation passed. Before recruiting anyone, open the HTML and exercise every scenario on the intended phone/browser with internet disabled; if it fails, fix it before sessions.

## Study goal and recruitment

Recruit 20–30 adults who buy groceries, including people who shop in Pakistan and, if possible, other English-speaking markets. Seek a range of ages, phone familiarity, shopping habits and language preferences. Do not collect names, phone numbers, receipts or financial information in the workbook; use anonymous codes (P01–P30). Keep any optional contact details in a separate consent-controlled location and delete after follow-up. Participation and follow-up are voluntary; explain how notes will be stored and ask permission before audio or video recording.

Recruitment message (send only with your own approval): “Hi! I am testing a simple tool that compares grocery pack prices. Would you be willing to spend about 15 minutes trying three short examples on your phone and answering a few questions? This is voluntary; you can stop at any time. No purchases or personal financial information are needed. If you agree, may I ask once more after your next shopping trip whether you used it?”

## Setup and fair comparison

1. Open `prototype/index.html` from the supplied ZIP and make sure scripts and CSS stay together. The facilitator must first check that it runs locally on the actual device; some mobile file managers restrict local files. Do not upload it to a public server containing participant data.
2. Randomly alternate task order: odd-numbered participants start with the prototype; even-numbered participants start with their normal calculator or another installed comparison app. Record the exact baseline and whether the participant knows it. The same tasks are used on both methods, so practice and order effects may inflate performance. For a stronger study, use matched alternate examples in a later iteration.
3. Say: “Please think aloud. Use the method naturally. I cannot tell you which controls to press. You may stop or skip a question.” Explain that price is the total for all packs. Do not coach while timing; note any assistance.
4. For each method and each task, start the timer when the full task is presented and stop when the participant states their final choice. Record seconds, choice, whether correct, and assistance in the workbook. Record abandoned tasks as incomplete, not as zero seconds. Do not count a task as independent if it required help.

## Three task cards and facilitator answer key

**Task 1 — equal package sizes.** Product A: Rs 120 for one 500 g pack; Product B: Rs 150 for one 500 g pack. Which costs less for the same weight? Answer: A, Rs 240/kg vs B, Rs 300/kg; A is 20% less than B per kg.

**Task 2 — multipack versus single pack.** Product A: Rs 320 total for two packs of 500 g each; Product B: Rs 500 for one 1 kg pack. Answer: A, Rs 320/kg vs B, Rs 500/kg; difference Rs 180/kg, 36% less than B.

**Task 3 — compatible volume and tie.** Product A: Rs 180 for one 750 ml bottle; Product B: Rs 240 for one 1 litre bottle. Answer: same unit price: Rs 240/litre.

## Post-task interview

1. Which method would you prefer for this shopping decision: study prototype, your normal method, or neither? Why?
2. Which labels or controls were confusing? What did you expect them to do?
3. Would you compare items this way during a real shopping trip? Why or why not?
4. What, if anything, would make using it faster: multipack entry, product labels, saved comparisons, a trip budget, or something else? Ask this after the participant gives an unprompted answer.
5. What language would you prefer? Would Urdu or Roman Urdu make the app easier? Do not assume a preference.
6. Would you use a price-comparison tool when the store already shows a per-unit price? If not, what would change your mind?

Record choice and short de-identified notes. Do not promise savings, represent illustrative prices as live market prices, or ask for Play ratings.

## Follow-up and scoring

Ask once after the next actual shopping trip only when the participant agreed: “Since the session, did you voluntarily use the comparison tool during a later shopping trip? If yes, what did you compare? If not, what got in the way?” Record Yes / No / Unknown. Never infer use from silence. The workbook reports both response rate and reuse among those reached, plus a conservative enrolled-group rate where Unknown is not credited as reuse.

Suggested decision thresholds, set before data collection: at least 20 enrolled participants; ≥80% complete all three prototype tasks correctly without assistance; ≥50% choose prototype as preferred; ≥40% of the entire tracked group report voluntary later-trip use. Count incomplete task and preference observations against their enrolled-group denominators. For reuse, unknowns do not count as use; if follow-up response is below 80%, report the decision as **inconclusive**, not as evidence of no demand. Treat any critical calculation or data-loss issue as a stop condition. Thresholds are study choices, not industry benchmarks. Record an honest decision and disagreements in the workbook; do not declare market validation from a small convenience sample.
