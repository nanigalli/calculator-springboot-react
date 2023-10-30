import { render, screen } from '@testing-library/react';
import Button from './Button';

test('render a button', () => {
    render(<Button key="MoreResults" className="moreResults" value="Load more results" />);
    const linkElement = screen.getByText('Load more results');
    expect(linkElement).toBeVisible;
});
