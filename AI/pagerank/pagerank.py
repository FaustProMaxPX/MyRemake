import os
import random
import re
import sys


DAMPING = 0.85
SAMPLES = 10000


def main():
    if len(sys.argv) != 2:
        sys.exit("Usage: python pagerank.py corpus")
    corpus = crawl(sys.argv[1])
    ranks = sample_pagerank(corpus, DAMPING, SAMPLES)
    print(f"PageRank Results from Sampling (n = {SAMPLES})")
    for page in sorted(ranks):
        print(f"  {page}: {ranks[page]:.4f}")
    ranks = iterate_pagerank(corpus, DAMPING)
    print(f"PageRank Results from Iteration")
    for page in sorted(ranks):
        print(f"  {page}: {ranks[page]:.4f}")


def crawl(directory):
    """
    Parse a directory of HTML pages and check for links to other pages.
    Return a dictionary where each key is a page, and values are
    a list of all other pages in the corpus that are linked to by the page.
    """
    pages = dict()

    # Extract all links from HTML files
    for filename in os.listdir(directory):
        if not filename.endswith(".html"):
            continue
        with open(os.path.join(directory, filename)) as f:
            contents = f.read()
            links = re.findall(r"<a\s+(?:[^>]*?)href=\"([^\"]*)\"", contents)
            pages[filename] = set(links) - {filename}

    # Only include links to other pages in the corpus
    for filename in pages:
        pages[filename] = set(
            link for link in pages[filename]
            if link in pages
        )

    return pages


def transition_model(corpus, page, damping_factor):
    """
    Return a probability distribution over which page to visit next,
    given a current page.

    With probability `damping_factor`, choose a link at random
    linked to by `page`. With probability `1 - damping_factor`, choose
    a link at random chosen from all pages in the corpus.
    """
    ret = dict()
    if (len(corpus[page]) == 0):
        return dict.fromkeys(corpus.keys, 1 / len(corpus))
    pos1 = (1 - damping_factor) / len(corpus)
    for k in corpus:
        ret[k] = pos1
    next_page = corpus[page]
    pos2 = damping_factor / len(next_page)
    for p in next_page:
        ret[p] += pos2
    return ret

def sample_pagerank(corpus, damping_factor, n):
    """
    Return PageRank values for each page by sampling `n` pages
    according to transition model, starting with a page at random.

    Return a dictionary where keys are page names, and values are
    their estimated PageRank value (a value between 0 and 1). All
    PageRank values should sum to 1.
    """
    
    page_rank = dict.fromkeys(corpus, 0)
    cur_page = random.choice(list(corpus.keys()))
    page_rank[cur_page] += 1
    for i in range(n - 1):
        possibilities = transition_model(corpus, cur_page, damping_factor)
        t = random.uniform(0, 1)
        x = 0
        for page, p in possibilities.items():
            x += p
            if x > t:
                cur_page = page
                page_rank[cur_page] += 1
                break
    for page in page_rank.keys():
        page_rank[page] /= n
    return page_rank
        
    


def iterate_pagerank(corpus, damping_factor):
    """
    Return PageRank values for each page by iteratively updating
    PageRank values until convergence.

    Return a dictionary where keys are page names, and values are
    their estimated PageRank value (a value between 0 and 1). All
    PageRank values should sum to 1.
    """
    N = len(corpus)
    page_rank = dict.fromkeys(corpus.keys(), 1 / N)
    num_links = {p:len(corpus[p]) for p in corpus}
    flag = True
    while flag:
        new_ranks = {}
        flag = False
        for page in page_rank:
            t = 0
            for p in corpus:
                if page in corpus[p]:
                    t += page_rank[p] / num_links[p]
            new_ranks[page] = (1 - damping_factor) / N + damping_factor * t
            if abs(page_rank[page] - new_ranks[page] > 1e-3):
                flag = True
        for p in new_ranks:
            page_rank[p] = new_ranks[p]
    return page_rank
        
        
            


if __name__ == "__main__":
    main()
